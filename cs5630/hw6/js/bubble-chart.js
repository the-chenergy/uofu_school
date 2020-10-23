/**
 * Controls the bubble chart element in the webpage.
 *
 * The State of the State of the States
 * Qianlang Chen
 * H 10/22/20
 */
class BubbleChart {
  //#region STATIC MEMBERS /////////////////////////////////////////////////////

  /** The maximum magnitude of margin this chart can show. */
  static MAX_MARGIN = 55;

  /** The margin steps to draw on the chart. */
  static MARGIN_STEPS = [-40, -20, 0, 20, 40];

  /** The radius of the bubble representing the maximum data value. */
  static MAX_BUBBLE_RADIUS = 10;

  /** The radius of the bubble representing the minimum data value. */
  static MIN_BUBBLE_RADIUS = -0.5;

  /** The width of the chart. */
  static CHART_WIDTH = 900;

  /** The height of each row in the chart table. */
  static ROW_HEIGHT = 132;

  //#endregion

  //#region CONSTRUCTOR AND INITIALIZATIONS ////////////////////////////////////

  /**
   * Creates a new BubbleChart instance.
   * @param {string} elementId The name of the HTML element which the bubble
   *     chart should be built on.
   */
  constructor(elementId) {
    let element = d3.select('#' + elementId);

    /**
     * @private @type {Selection} The selection containing the chart's header.
     */
    this.header = element.append('div').attr('id', 'bubble-chart-header');

    /**
     * @private @type {Selection} The selection containing the chart's table.
     */
    this.table = element.append('table')
                     .attr('class', 'table')
                     .attr('id', 'bubble-chart-table');

    /**
     * @private @type {function(number):number} A scale that converts a data
     *     value into an X location in the chart.
     */
    this.xScale = d3.scaleLinear().domain([-51, BubbleChart.MAX_MARGIN]).range([
      0, BubbleChart.CHART_WIDTH
    ]);

    /** @private @type {*[]} The raw data array. */
    this.data = null;

    /**
     * @private @type {function(number):number} A scale that converts a data
     *     value into the radius of a bubble.
     */
    this.rScale = null;

    /** @private @type {InfoPanel} The main info panel. */
    this.mainInfoPanel = null;

    /**
     * @private @type {Selection} The selection containing the chart content
     *     container.
     */
    this.contents = null;

    /** @private @type {Bubble[]} The list of bubbles in display. */
    this.bubbles = [];

    /**
     * @private @type {Map<string, Bubble[]>} The lists of bubbles, by
     *     categories.
     */
    this.bubblesByCategories = new Map();

    /** @private @type {string[]} The list of categories in the data. */
    this.categories = null;

    /** @private @type {number} The number of rows the chart has. */
    this.numRows = 0;

    /** @private @type {*[]} The brush collection (for each row). */
    this.brushes = [];

    /** @private @type {Bubble} The low extreme bubble. */
    this.lowExtreme = null;

    /** @private @type {Bubble} The high extreme bubble. */
    this.highExtreme = null;

    /** @type {boolean} Whether the bubbles are grouped by categories. */
    this.isGrouped = false;

    /** @type {boolean} Whether it's currently showing the extreme bubbles. */
    this.isShowingExtremes = false;

    /** @type {boolean} Whether there are bubbles currently selected. */
    this.hasSelections = false;

    /**
     * @type {function(Set<number>)} The callback function used when the bubble
     *     selection changes.
     */
    this.onSelectionChange = null;

    this.initHeader();
    this.initTable();

    d3.select(document).on('mousedown', e => {
      if (e.target.getAttribute('id') == 'bubble-chart-show-extremes-button') {
        this.clearBrushSelections();
        return;
      }

      this.clearAll();
    });
  }

  /** @private Initializes the chart's header. */
  initHeader() {
    let options = this.header.append('div')
                      .attr('class', 'custom-control custom-switch')
                      .attr('id', 'bubble-chart-group-topics-switch');
    options.append('input')
        .attr('type', 'checkbox')
        .attr('class', 'custom-control-input')
        .attr('id', 'bubble-chart-group-topics-switch-checkbox')
        .on('change', e => {
          this.isGrouped = e.target.checked;
          this.updateBubbles();
        });
    options.append('label')
        .attr('class', 'custom-control-label')
        .attr('id', 'bubble-chart-group-topics-switch-label')
        .attr('for', 'bubble-chart-group-topics-switch-checkbox')
        .style('vertical-align', 'middle')
        .text('Grouped by Topics');
    options.append('button')
        .attr('type', 'button')
        .attr('class', 'btn btn-outline-secondary btn-sm')
        .attr('id', 'bubble-chart-show-extremes-button')
        .text('Show Extremes')
        .on('click', () => {
          if (this.isShowingExtremes)
            this.hideExtremes();
          else
            this.showExtremes();
        });
  }

  /** @private Initializes the chart's main table. */
  initTable() {
    let tableHeader =
        this.table.append('thead').attr('id', 'bubble-chart-table-header');
    let headerParties = tableHeader.append('tr').append('td').attr(
        'id', 'bubble-chart-table-header-parties');
    headerParties.append('span')
        .attr('class', 'democrat')
        .html('Leans Democrat <i class="fas fa-angle-left"></i>');
    headerParties.append('span')
        .attr('class', 'republican')
        .style('float', 'right')
        .html('<i class="fas fa-angle-right"></i> Leans Republican');

    let headerLegends = tableHeader.append('tr')
                            .append('td')
                            .append('svg')
                            .attr('id', 'bubble-chart-table-header-legends')
                            .attr('width', BubbleChart.CHART_WIDTH)
                            .attr('height', 36);
    headerLegends.selectAll('text')
        .data(BubbleChart.MARGIN_STEPS)
        .enter()
        .append('text')
        .attr('class', d => partyClassOf(d))
        .text(d => (d == 0 ? '0' : '+' + Math.abs(d)))
        .attr('x', d => this.xScale(d) - 6 - (d != 0) * 12)
        .attr('y', 24);
  }

  /**
   * @private Initializes the chart's contents after the data is read in and
   *     processed.
   */
  initContents() {
    this.contents = this.table.append('tbody')
                        .append('tr')
                        .append('td')
                        .append('svg')
                        .attr('id', 'bubble-chart-contents')
                        .attr('width', BubbleChart.CHART_WIDTH)
                        .attr('height', BubbleChart.ROW_HEIGHT * this.numRows);

    // Draw legend lines.
    this.contents.append('g')
        .attr('id', 'bubble-chart-contents-first-row-lines')
        .selectAll('line')
        .data(BubbleChart.MARGIN_STEPS)
        .enter()
        .append('line')
        .attr('class', d => partyClassOf(d) + '-stroke')
        .attr('x1', d => this.xScale(d))
        .attr('x2', d => this.xScale(d))
        .attr('y1', 0)
        .attr('y2', BubbleChart.ROW_HEIGHT)
        .style('opacity', 0.1667);

    this.contents.append('g')
        .attr('id', 'bubble-chart-contents-other-row-lines')
        .selectAll('line')
        .data(BubbleChart.MARGIN_STEPS)
        .enter()
        .append('line')
        .attr('class', d => partyClassOf(d) + '-stroke')
        .attr('x1', d => this.xScale(d))
        .attr('x2', d => this.xScale(d))
        .attr('y1', BubbleChart.ROW_HEIGHT)
        .attr('y2', BubbleChart.ROW_HEIGHT * this.numRows)
        .style('opacity', 0);

    // Draw category titles.
    let titles =
        this.contents.append('g').attr('id', 'bubble-chart-contents-titles');
    titles.selectAll('rect')
        .data(this.categories)
        .enter()
        .append('rect')
        .attr('y', (_d, i) => BubbleChart.ROW_HEIGHT * i)
        .attr('width', d => d.length * 8)
        .attr('height', 24)
        .style('opacity', this.isGrouped * 1)
        .style('fill', 'white');
    titles.selectAll('text')
        .data(this.categories)
        .enter()
        .append('text')
        .attr('class', d => categoryClassOf(d))
        .attr('y', (_d, i) => BubbleChart.ROW_HEIGHT * i + 18)
        .style('opacity', this.isGrouped * 1)
        .style('user-select', this.isGrouped ? '' : 'none')
        .text(d => d);

    // Create brushes.
    for (let i = 0; i < this.numRows; i++) {
      let brush = d3.brushX().extent([
        [0, i * BubbleChart.ROW_HEIGHT],
        [BubbleChart.CHART_WIDTH, (i + 1) * BubbleChart.ROW_HEIGHT]
      ]);
      brush.on('brush', e => this.updateBrushSelections(e));
      this.brushes.push(brush);

      this.contents.append('g')
          .attr('id', 'bubble-chart-contents-brush-row' + i)
          .style('display', !this.isGrouped && i > 0 ? 'none' : '')
          .call(brush);
    }

    // Draw bubbles.
    this.contents.append('g')
        .attr('id', 'bubble-chart-contents-bubbles')
        .selectAll('circle')
        .data(this.bubbles)
        .enter()
        .append('circle')
        .attr('class', d => d.category)
        .attr('bubble-id', d => d.id)
        .attr('cx', d => d.cx[this.isGrouped])
        .attr('cy', d => d.cy[this.isGrouped] + BubbleChart.ROW_HEIGHT / 2)
        .attr('r', d => d.r)
        .on('mouseover', e => this.showActiveBubbleInfo(e.target))
        .on('mouseout', () => this.mainInfoPanel.hide());

    // Create info panels.
    this.header.append('g')
        .style('position', 'absolute')
        .append('div')
        .attr('id', 'bubble-chart-low-info-panel')
        .style('background', '#e8e8ff');
    this.header.append('g')
        .style('position', 'absolute')
        .append('div')
        .attr('id', 'bubble-chart-high-info-panel')
        .style('background', '#ffe8e8');
    this.header.append('g')
        .style('position', 'absolute')
        .append('div')
        .attr('id', 'bubble-chart-main-info-panel');
    this.lowInfoPanel = new InfoPanel('bubble-chart-low-info-panel');
    this.highInfoPanel = new InfoPanel('bubble-chart-high-info-panel');
    this.mainInfoPanel = new InfoPanel('bubble-chart-main-info-panel');
  }

  //#endregion

  //#region METHODS ////////////////////////////////////////////////////////////

  /**
   * Reads and processes the raw data of phrases.
   * @param {*[]} rawData The raw CSV data read in.
   */
  setData(rawData) {
    this.data = rawData;

    // Create radius scale.
    let maxTotal = Math.max(...rawData.map(d => d['total']));
    this.rScale = d3.scaleSqrt().domain([0, maxTotal]).range([
      BubbleChart.MIN_BUBBLE_RADIUS, BubbleChart.MAX_BUBBLE_RADIUS
    ]);

    // Create bubble objects and assign categories.
    for (let item of rawData) {
      let category = toTitleCase(item['category']);
      if (!this.bubblesByCategories.has(category))
        this.bubblesByCategories.set(category, []);

      let bubble = new Bubble(item, this.rScale);
      this.bubblesByCategories.get(category).push(bubble);
      this.bubbles.push(bubble);

      if (!this.lowExtreme || bubble.cx[false] < this.lowExtreme.cx[false])
        this.lowExtreme = bubble;

      if (!this.highExtreme || bubble.cx[false] > this.highExtreme.cx[false])
        this.highExtreme = bubble;
    }
    this.categories = Array.from(this.bubblesByCategories.keys());
    this.numRows = this.categories.length;

    this.initContents();
  }

  /**
   * @private Updates the locations of the bubbles after a toggle of
   *     group-topic.
   */
  updateBubbles() {
    d3.select('#bubble-chart-contents-bubbles')
        .selectAll('circle')
        .transition()
        .duration(260)
        .ease(d3.easeExpOut)
        .attr('cx', d => d.cx[this.isGrouped])
        .attr('cy', d => d.cy[this.isGrouped] + BubbleChart.ROW_HEIGHT / 2);

    d3.select('#bubble-chart-contents-other-row-lines')
        .selectAll('line')
        .transition()
        .duration(260)
        .ease(this.isGrouped ? d3.easeSinIn : d3.easeExpOut)
        .style('opacity', this.isGrouped * 0.1667);

    d3.select('#bubble-chart-contents-titles')
        .selectAll('text')
        .transition()
        .duration(260)
        .ease(this.isGrouped ? d3.easeSinIn : d3.easeExpOut)
        .style('opacity', this.isGrouped * 1)
        .style('user-select', this.isGrouped ? '' : 'none');

    d3.select('#bubble-chart-contents-titles')
        .selectAll('rect')
        .transition()
        .duration(260)
        .ease(this.isGrouped ? d3.easeSinIn : d3.easeExpOut)
        .style('opacity', this.isGrouped * 1)
        .style('user-select', this.isGrouped ? '' : 'none');

    this.clearBrushSelections();
    for (let i = 0; i < this.numRows; i++) {
      d3.select('#bubble-chart-contents-brush-row' + i)
          .style('display', !this.isGrouped && i > 0 ? 'none' : '');
    }
  }

  /**
   * @private Shows the main info-panel with the information of some active
   *     bubble.
   * @param {HTMLElement} eventTarget The event target.
   */
  showActiveBubbleInfo(eventTarget) {
    let bubble = this.bubbles[d3.select(eventTarget).attr('bubble-id')];
    this.showBubbleInfo(bubble, this.mainInfoPanel);
  }

  /**
   * @private Shows the main info-panel with the information of some active
   *     bubble.
   * @param {Bubble} bubble The bubble to show info.
   * @param {InfoPanel} infoPanel The info-panel to show.
   */
  showBubbleInfo(bubble, infoPanel) {
    let offset = document.getElementById('bubble-chart-table')
                     .getBoundingClientRect(),
        x = bubble.cx[this.isGrouped] + 12;
    if (x + 288 > offset.width) x -= 324;
    let y = Math.max(bubble.cy[this.isGrouped] - 12, -offset.y + 12),
        title = toTitleCase(bubble.rawData['phrase']),
        category = toTitleCase(bubble.rawData['category']),
        categoryClass = bubble.category,
        marginValue = +bubble.rawData['position'],
        party = marginValue < 0 ? 'Democrat' : 'Republican',
        partyClass = partyClassOf(marginValue || 1);
    marginValue = Math.abs(marginValue);
    let margin = marginValue < 0.1 ? '<0.1%' : marginValue.toFixed(1) + '%',
        speeches = ((+bubble.rawData['total'] / 50) * 100).toFixed(0) + '%',
        html = `<h4>${title}</h4>` +
        `<em class="${categoryClass}">${category}</em><br>` +
        `Mentioned by ${party}s ` +
        `<span class="${partyClass}">${margin}</span> more<br>` +
        `In <span>${speeches}</span> of speeches by both Parties`;

    infoPanel.show(x, y, html);
  }

  /** @private Clear all brush selections. */
  clearBrushSelections() {
    if (!this.hasSelections) return;

    for (let i = 0; i < this.numRows; i++) {
      this.contents.select('#bubble-chart-contents-brush-row' + i)
          .call(this.brushes[i].clear);
    }

    this.updateBubbleSelections(null);
    this.hasSelections = false;
  }

  /**
   * @private Calculates for all circles which ones fall into the active brush's
   *     selection.
   * @param {number[]} e The event object containing the active brush.
   */
  updateBrushSelections(e) {
    if (!e.selection) {
      this.contents.selectAll('circle').attr('class', d => d.category);
      return;
    }

    let activeRange = e.target.extent().call(), selectionLowX = e.selection[0],
        selectionHighX = e.selection[1], selectionLowY = activeRange[0][1],
        selectionHighY = activeRange[1][1];

    let selectedBubbleIds = new Set();
    for (let bubble of this.bubbles) {
      let bubbleX = bubble.cx[this.isGrouped],
          bubbleY = bubble.cy[this.isGrouped] + BubbleChart.ROW_HEIGHT / 2;
      if (bubbleX >= selectionLowX && bubbleY >= selectionLowY &&
          bubbleX < selectionHighX && bubbleY < selectionHighY)
        selectedBubbleIds.add(bubble.id);
    }

    this.updateBubbleSelections(selectedBubbleIds);
  }

  /**
   * @private Updates the bubbles' colors according to their selection statuses.
   * @param {Set<number>} selectedBubbleIds The IDs of the selected bubbles.
   */
  updateBubbleSelections(selectedBubbleIds) {
    if (this.onSelectionChange) this.onSelectionChange(selectedBubbleIds);
    this.hasSelections = false;

    if (!selectedBubbleIds) {
      this.contents.selectAll('circle').attr('class', d => d.category);
      return;
    }
    this.hasSelections = true;

    this.contents.selectAll('circle').attr(
        'class',
        d => d.category + (selectedBubbleIds.has(d.id) ? '' : ' unselected'));
  }

  /** @private Shows the extreme bubbles. */
  showExtremes() {
    if (!this.lowExtreme || !this.highExtreme) return;
    this.isShowingExtremes = true;

    this.updateBubbleSelections(
        new Set([this.lowExtreme.id, this.highExtreme.id]));
    this.showBubbleInfo(this.lowExtreme, this.lowInfoPanel);
    this.showBubbleInfo(this.highExtreme, this.highInfoPanel);

    d3.selectAll('[id^="bubble-chart-table-"]').style('opacity', .3333);
    d3.selectAll('[id^="bubble-chart-contents-"]').style('opacity', .3333);

    d3.select('#bubble-chart-contents-bubbles').style('opacity', 1);
  }

  /** @private Hides the extreme bubbles. */
  hideExtremes() {
    if (!this.isShowingExtremes) return;
    this.isShowingExtremes = false;

    this.updateBubbleSelections(null);
    this.lowInfoPanel.hide();
    this.highInfoPanel.hide();

    d3.selectAll('[id^="bubble-chart-table-"]').style('opacity', 1);
    d3.selectAll('[id^="bubble-chart-contents-"]').style('opacity', 1);
  }

  /** @private Clear all selections and the extremes. */
  clearAll() {
    if (this.isShowingExtremes)
      this.hideExtremes();
    else
      this.clearBrushSelections();
  }

  //#endregion
}

/** Represents a bubble displayed in the bubble-chart. */
class Bubble {
  /**
   * Creates a new Bubble instance.
   * @param {*} rawDataItem The phrase data item corresponding to this bubble.
   * @param {function(number):number} rScale The scale for the bubble's radius.
   */
  constructor(rawDataItem, rScale) {
    /** @type {*} The raw data item. */
    this.rawData = rawDataItem;

    /** @type {number} The unique ID of this bubble. */
    this.id = rawDataItem['id'];

    /** @type {string} The category of the data value this bubble represents. */
    this.category = categoryClassOf(rawDataItem['category']);

    /** @type {*} The bubble's X location on screen. */
    this.cx = {false: rawDataItem['sourceX'], true: rawDataItem['moveX']};

    /** @type {*} The bubble's Y location on screen. */
    this.cy = {false: rawDataItem['sourceY'], true: rawDataItem['moveY']};

    /** @type {number} The bubble's radius. */
    this.r = rScale(rawDataItem['total']);
  }
}
