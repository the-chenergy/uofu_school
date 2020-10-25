/**
 * Controls the phrase stat table element in the webpage.
 *
 * The State of the State of the States
 * Qianlang Chen
 * A 10/24/20
 */
class PhraseTable {
  //#region STATIC MEMBERS /////////////////////////////////////////////////////

  /** The list of columns the table has. */
  static COLUMNS = ['phrase', 'proportion', 'frequency'];

  /** The list of the titles of the columns. */
  static COLUMN_TITLES = ['Phrase', 'Frequency By Party', 'Total Frequency'];

  /** The widths of the columns. */
  static COLUMN_WIDTHS = [216, 432, 216];

  /** The maximum proportion the proportion-column can show. */
  static MAX_PROPORTION = 100;

  /** The proportion steps to draw as legends the proportion-column. */
  static PROPORTION_STEPS = [-75, -50, -25, 0, 25, 50, 75];

  /** The proportion steps to draw on the bars in the proportion-column. */
  static PROPORTION_BAR_STEPS = [-100, -75, -50, -25, 0, 25, 50, 75, 100];

  /** The maximum frequency the frequency-column can show. */
  static MAX_FREQUENCY = 100;

  /** The frequency steps to draw on the frequency-column. */
  static FREQUENCY_STEPS = [25, 50, 75];

  /** The frequency steps to draw on the bars in the frequency-column. */
  static FREQUENCY_BAR_STEPS = [0, 25, 50, 75, 100];

  /** The height of each row in the table. */
  static ROW_HEIGHT = 24;

  /** @type {(function(Phrase, Phrase):number)[][]} The sorting functions. */
  static SORT_FUNCS = [
    // phrase-column
    [
      (a, b) => a.phrase.localeCompare(b.phrase),
      (a, b) => b.phrase.localeCompare(a.phrase),
    ],
    // proportion-column
    [
      (a, b) => b.dProportion - a.dProportion,
      (a, b) => b.rProportion - a.rProportion,
    ],
    // frequency-column
    [
      (a, b) => a.frequency - b.frequency,
      (a, b) => b.frequency - a.frequency,
    ],
  ];

  //#endregion

  //#region CONSTRUCTOR AND INITIALIZATIONS ////////////////////////////////////

  /**
   * Creates a new PhraseTable instance.
   * @param {string} elementId The name of the HTML element which the table
   *     should be built on.
   */
  constructor(elementId) {
    let element = d3.select('#' + elementId);

    /**
     * @private @type {Selection} The selection containing the table's header.
     */
    this.header = element.append('table').append('thead').append('tr').attr(
        'id', 'phrase-table-header');

    /**
     * @private @type {Selection} The selection containing the table's contents.
     */
    this.contents = element.append('table').append('tbody').attr(
        'id', 'phrase-table-contents');

    /** @private @type {InfoPanel} The main info panel. */
    this.mainInfoPanel = null;

    /**
     * @private @type {function(number):number} A scale that converts a
     *     proportion data value into an X location in the proportion-column.
     */
    this.proportionXScale =
        d3.scaleLinear()
            .domain([-PhraseTable.MAX_PROPORTION, PhraseTable.MAX_PROPORTION])
            .range([0, PhraseTable.COLUMN_WIDTHS[1]]);

    /**
     * @private @type {function(number):number} A scale that converts a
     *     frequency data value into an X location in the frequency-column.
     */
    this.frequencyXScale =
        d3.scaleLinear().domain([0, PhraseTable.MAX_FREQUENCY]).range([
          0, PhraseTable.COLUMN_WIDTHS[2]
        ]);

    /** @private @type {Array} The raw data array. */
    this.data = null;

    /** @private @type {Phrase[]} The list of phrases. */
    this.phrases = [];

    /** @private @type {Phrase[]} The list of phrases in display. */
    this.phrasesInDisplay = [];

    /** @private @type {number[]} The sorting function currently in use. */
    this.currentSortFunc = [0, 0];

    /**
     * @private @type {Set<number>} The IDs of the phrases selected to be
     *     filtered.
     */
    this.selectedPhraseIds = null;

    this.initHeader();
    this.initContents();
  }

  /** @private Initializes the table's header. */
  initHeader() {
    this.header.selectAll('td')
        .data(PhraseTable.COLUMNS)
        .enter()
        .append('td')
        .attr('id', d => 'phrase-table-header-' + d)
        .style('width', (_d, i) => PhraseTable.COLUMN_WIDTHS[i] + 'px')
        .on('click',
            (_e, d) =>
                this.updateCurrentSortFunc(PhraseTable.COLUMNS.indexOf(d), 1))
        .append('div')
        .text((_d, i) => PhraseTable.COLUMN_TITLES[i])
    // .style('margin-top', (_d, i) => i > 0 ? '' : '-16px');

    let phraseLegends = d3.select('#phrase-table-header-phrase');
    phraseLegends.append('div')
        .attr('class', partyClassOf(0))
        .attr('id', 'phrase-table-header-phrase-legend');

    let proportionLegends = d3.select('#phrase-table-header-proportion');
    proportionLegends.append('g')
        .attr('id', 'phrase-table-header-proportion-legends')
        .selectAll('text')
        .data(PhraseTable.PROPORTION_STEPS)
        .enter()
        .append('text')
        .attr('class', d => partyClassOf(d))
        .style('left', d => this.proportionXScale(d) - 12 - (d != 0) * 4 + 'px')
        .text(d => Math.abs(d) + '%');

    let frequencyLegends = d3.select('#phrase-table-header-frequency');
    frequencyLegends.append('g')
        .attr('id', 'phrase-table-header-frequency-legends')
        .selectAll('text')
        .data(PhraseTable.FREQUENCY_STEPS)
        .enter()
        .append('text')
        .attr('class', partyClassOf(0))
        .style('left', d => this.frequencyXScale(d) - 8 - (d != 0) * 8 + 'px')
        .text(d => d + '%');
  }

  /** @private Initializes the table's contents. */
  initContents() {
    this.contents.append('div')
        .attr('id', 'phrase-table-main-info-panel')
        .style('position', 'absolute');
    this.mainInfoPanel = new InfoPanel('phrase-table-main-info-panel');
  }

  //#endregion

  //#region METHODS ////////////////////////////////////////////////////////////

  /**
   * Reads and processes the raw data of phrases.
   * @param {*[]} rawData The raw CSV data read in.
   */
  setData(rawData) {
    this.data = rawData;

    for (let item of rawData) {
      let phrase =
          new Phrase(item, this.proportionXScale, this.frequencyXScale);
      this.phrases.push(phrase);
    }
    this.phrasesInDisplay = this.phrases;

    this.updateCurrentSortFunc(0, 0);
  }

  /**
   * Select some phrases to only show them in the table.
   * @param {Set<number>} selectedPhraseIds The IDs of the phrases to select.
   */
  selectPhrases(selectedPhraseIds) {
    this.selectedPhraseIds = selectedPhraseIds;

    if (!selectedPhraseIds) {
      this.contents.selectAll('tr').style('display', '');
      return;
    }

    this.contents.selectAll('tr').style(
        'display', d => selectedPhraseIds.has(d.id) ? '' : 'none');
  }

  /**
   * @private Updates the current sorting function.
   * @param {number} sortColumn The index of the column to sort.
   * @param {number} sortFuncIncrement The increment for the sorting function
   *     change.
   */
  updateCurrentSortFunc(sortColumn, sortFuncIncrement) {
    if (sortColumn != this.currentSortFunc[0]) {
      this.updateHeader(this.currentSortFunc[0], -1);
      this.currentSortFunc[0] = sortColumn;
      this.currentSortFunc[1] = 0;
    } else {
      this.currentSortFunc[1] += sortFuncIncrement;
      this.currentSortFunc[1] %= PhraseTable.SORT_FUNCS[sortColumn].length;
    }

    this.updateHeader(sortColumn, this.currentSortFunc[1]);
    this.sortPhrases();
    this.updatePhrases();
  }

  /**
   * @private Updates a column according to the current sorting function.
   * @param {number} column The column's index.
   * @param {number} sortFuncIndex The sorting function in use.
   */
  updateHeader(column, sortFuncIndex) {
    let element =
        this.header
            .select('#phrase-table-header-' + PhraseTable.COLUMNS[column])
            .select('div');
    let html = PhraseTable.COLUMN_TITLES[column];

    if (sortFuncIndex >= 0) {
      if (column == 0) {
        html += sortFuncIndex == 0 ? ' <i class="fas fa-angle-up"></i>' :
                                     ' <i class="fas fa-angle-down"></i>';
      } else {
        if (sortFuncIndex == 0)
          html = '<i class="fas fa-angle-left"></i> ' + html;
        else
          html += ' <i class="fas fa-angle-right"></i>';
      }
    }

    element.html(html);
  }

  /**
   * @private Sorts the phrases in display using the current sorting function.
   */
  sortPhrases() {
    this.phrasesInDisplay = this.phrasesInDisplay.sort(
        PhraseTable
            .SORT_FUNCS[this.currentSortFunc[0]][this.currentSortFunc[1]]);
  }

  /** @private Updates the rows in display. */
  updatePhrases() {
    let rows = this.contents.selectAll('tr').data(this.phrasesInDisplay);
    rows.exit().remove();

    // Create new rows if necessary.
    let newRows = rows.enter().append('tr');

    let phraseColumn = newRows.append('td').style(
        'width', PhraseTable.COLUMN_WIDTHS[0] + 'px');
    phraseColumn.append('div')
        .attr('id', 'phrase-table-phrase-column-text')
        .style('width', PhraseTable.COLUMN_WIDTHS[0] + 'px');

    let proportionColumn =
        newRows.append('td')
            .style('width', PhraseTable.COLUMN_WIDTHS[1] + 'px')
            .append('svg')
            .attr('id', 'phrase-table-proportion-column-svg')
            .attr('width', PhraseTable.COLUMN_WIDTHS[1])
            .attr('height', PhraseTable.ROW_HEIGHT);
    proportionColumn.append('rect')
        .attr('class', 'democrat')
        .attr('id', 'phrase-table-proportion-column-bar-democrat');
    proportionColumn.append('rect')
        .attr('class', 'republican')
        .attr('id', 'phrase-table-proportion-column-bar-republican');
    proportionColumn.selectAll('line')
        .data(PhraseTable.PROPORTION_BAR_STEPS)
        .enter()
        .append('line')
        .style('stroke', 'white')
        .style('stroke-width', d => (d % 100 == 0 ? 4 : 1) + 'px')
        .attr('x1', d => this.proportionXScale(d))
        .attr('y1', 0)
        .attr('x2', d => this.proportionXScale(d))
        .attr('y2', PhraseTable.ROW_HEIGHT);

    let frequencyColumn =
        newRows.append('td')
            .style('width', PhraseTable.COLUMN_WIDTHS[2] + 'px')
            .append('svg')
            .attr('id', 'phrase-table-frequency-column-svg')
            .attr('width', PhraseTable.COLUMN_WIDTHS[2])
            .attr('height', PhraseTable.ROW_HEIGHT);
    frequencyColumn.append('rect').attr(
        'id', 'phrase-table-frequency-column-bar');
    frequencyColumn.selectAll('line')
        .data(PhraseTable.FREQUENCY_BAR_STEPS)
        .enter()
        .append('line')
        .style('stroke', 'white')
        .style('stroke-width', d => (d % 100 == 0 ? 4 : 1) + 'px')
        .attr('x1', d => this.frequencyXScale(d))
        .attr('y1', 0)
        .attr('x2', d => this.frequencyXScale(d))
        .attr('y2', PhraseTable.ROW_HEIGHT);
    frequencyColumn.append('text').attr(
        'id', 'phrase-table-frequency-column-text');

    rows = newRows.merge(rows);

    // Update the old rows.
    let i = 0, j = 0, k = 0;  // #screw_d3
    rows.attr('phrase-id', () => this.phrasesInDisplay[i++].id);

    i = 0, j = 0;
    rows.selectAll('#phrase-table-phrase-column-text')
        .text(() => truncateAt(this.phrasesInDisplay[i++].phrase, 21))
        .attr(
            'title',
            () => this.phrasesInDisplay[j++].phrase.length > 21 ?
                this.phrasesInDisplay[j - 1].phrase :
                '');

    i = 0, j = 0;
    rows.selectAll('#phrase-table-proportion-column-bar-democrat')
        .attr('x', () => this.phrasesInDisplay[i++].dProportionBarX)
        .attr('y', PhraseTable.ROW_HEIGHT * .125)
        .attr('width', () => this.phrasesInDisplay[j++].dProportionBarWidth)
        .attr('height', PhraseTable.ROW_HEIGHT * .75);
    i = 0, j = 0;
    rows.selectAll('#phrase-table-proportion-column-bar-republican')
        .attr('x', () => this.phrasesInDisplay[i++].rProportionBarX)
        .attr('y', PhraseTable.ROW_HEIGHT * .125)
        .attr('width', () => this.phrasesInDisplay[j++].rProportionBarWidth)
        .attr('height', PhraseTable.ROW_HEIGHT * .75);
    i = 0;
    rows.selectAll('#phrase-table-proportion-column-svg')
        .attr('phrase-id', () => i++)
        .on('mouseover', e => this.showActiveProportionInfo(e.target))
        .on('mouseout', () => this.mainInfoPanel.hide());

    i = 0, j = 0, k = 0;
    rows.selectAll('#phrase-table-frequency-column-bar')
        .attr('class', () => this.phrasesInDisplay[i++].categoryClass)
        .attr('x', () => this.phrasesInDisplay[j++].frequencyBarX)
        .attr('y', PhraseTable.ROW_HEIGHT * .125)
        .attr('width', () => this.phrasesInDisplay[k++].frequencyBarWidth)
        .attr('height', PhraseTable.ROW_HEIGHT * .75);
    i = 0, j = 0, k = 0;
    rows.selectAll('#phrase-table-frequency-column-text')
        .attr(
            'x',
            () => this.phrasesInDisplay[i].frequencyBarWidth +
                (this.phrasesInDisplay[i++].frequency > 43 ? -21 : 3))
        .attr('y', 16)
        .style('font-size', 'small')
        .style(
            'fill',
            () => this.phrasesInDisplay[j++].frequency > 43 ? 'white' : 'black')
        .text(() => this.phrasesInDisplay[k++].frequency.toString());
    i = 0;
    rows.selectAll('#phrase-table-frequency-column-svg')
        .attr('phrase-id', () => i++)
        .on('mouseover', e => this.showActiveFrequencyInfo(e.target))
        .on('mouseout', () => this.mainInfoPanel.hide());

    if (this.selectedPhraseIds) this.selectPhrases(this.selectedPhraseIds);
  }

  /**
   * @private Shows the main info-panel with the information of the proportion
   *     info for some active phrase.
   * @param {HTMLElement} eventTarget The event target.
   */
  showActiveProportionInfo(eventTarget) {
    if (eventTarget.tagName != 'svg') eventTarget = eventTarget.parentNode;

    let tableOffset =
            document.getElementById('phrase-table').getBoundingClientRect(),
        rowOffset = eventTarget.getBoundingClientRect(),
        x = tableOffset.width / 2 - 138, y = rowOffset.y - tableOffset.y - 72;
    if (y < -tableOffset.y + 12) y += 102;

    let phrase =
        this.phrasesInDisplay[+d3.select(eventTarget).attr('phrase-id')];
    let html = `In <span class="democrat">${phrase.dProportion}%</span>` +
        ` of Democratic speeches<br>` +
        `In <span class="republican">${phrase.rProportion}%</span>` +
        ` of Republican speeches`;

    this.mainInfoPanel.show(x, y, html);
  }

  /**
   * @private Shows the main info-panel with the information of the frequency
   *     info for some active phrase.
   * @param {HTMLElement} eventTarget The event target.
   */
  showActiveFrequencyInfo(eventTarget) {
    if (eventTarget.tagName != 'svg') eventTarget = eventTarget.parentNode;

    let tableOffset =
            document.getElementById('phrase-table').getBoundingClientRect(),
        rowOffset = eventTarget.getBoundingClientRect(), x = 642,
        y = rowOffset.y - tableOffset.y - 72;
    if (y < -tableOffset.y + 12) y += 102;

    let phrase =
        this.phrasesInDisplay[+d3.select(eventTarget).attr('phrase-id')];
    let html = `In <span>${phrase.frequency}</span> out of 50<br>` +
        `speeches ` +
        `<span>(${Math.round(phrase.frequency / 50 * 100)}%)</span>`;

    this.mainInfoPanel.show(x, y, html);
  }

  //#endregion
}

/** Represents a phrase (row) in the phrase-table. */
class Phrase {
  /**
   * Creates a new Bubble instance.
   * @param {*} rawDataItem The phrase data item corresponding to this phrase.
   * @param {function(number):number} proportionXScale The scale for the
   *     proportion bars' locations and lengths.
   * @param {function(number):number} frequencyXScale The scale for the
   *     frequency bar's lengths.
   */
  constructor(rawDataItem, proportionXScale, frequencyXScale) {
    /** @type {*} The raw data item. */
    this.rawData = rawDataItem;

    /** @type {number} The unique ID of this phrase. */
    this.id = rawDataItem['id'];

    /** @type {string} The category of the phrase. */
    this.category = toTitleCase(rawDataItem['category']);

    /** @type {string} The category class of the phrase. */
    this.categoryClass = categoryClassOf(this.category);

    /** @type {string} The phrase. */
    this.phrase = toTitleCase(rawDataItem['phrase']);

    /** @type {number} The proportion of Democratic speeches. */
    this.dProportion = Math.round(rawDataItem['percent_of_d_speeches']);

    /**
     * @type {number} The X location of the bar representing the proportion of
     *     Democrat speeches.
     */
    this.dProportionBarX =
        proportionXScale(-rawDataItem['percent_of_d_speeches']);

    /**
     * @type {number} The width of the bar representing the proportion of
     *     Democrat speeches.
     */
    this.dProportionBarWidth = proportionXScale(0) - this.dProportionBarX;

    /** @type {number} The proportion of Republican speeches. */
    this.rProportion = Math.round(rawDataItem['percent_of_r_speeches']);

    /**
     * @type {number} The X location of the bar representing the proportion of
     *     Republican speeches.
     */
    this.rProportionBarX = proportionXScale(0);

    /**
     * @type {number} The width of the bar representing the proportion of
     *     Republican speeches.
     */
    this.rProportionBarWidth =
        proportionXScale(+rawDataItem['percent_of_r_speeches']) -
        this.rProportionBarX;

    /** @type {number} The number of speeches that the phrase appeared in. */
    this.frequency = +rawDataItem['total'];

    /**
     * @type {number} The X location of the bar representing the
     *     speech-frequency.
     */
    this.frequencyBarX = frequencyXScale(0);

    /**
     * @type {number} The width of the bar representing the speech-frequency.
     */
    this.frequencyBarWidth =
        frequencyXScale(this.frequency / 50 * 100) - this.frequencyBarX;
  }
}
