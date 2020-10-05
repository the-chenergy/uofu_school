/** Class implementing the table. */
class Table
{
  /**
   * Creates a Table Object
   */
  constructor(forecastData, pollData)
  {
    forecastData.sort((a, b) => a['state'].localeCompare(b['state']));
    forecastData.sort((a, b) => Math.abs(a['margin']) - Math.abs(b['margin']));

    this.tableData = [...forecastData ];
    this.tableDataLookup = new Map();

    // add useful attributes
    let round = x => Math.floor(x * 100) > 99 ? 100 : Math.round(x * 100);
    for (let forecast of this.tableData)
    {
      forecast.isForecast = true;
      forecast.isExpanded = false;

      forecast['winstate_inc'] = round(forecast['winstate_inc']);
      forecast['winstate_chal'] = round(forecast['winstate_chal']);

      this.tableDataLookup.set(forecast['state'], forecast);
    }
    this.pollData = pollData;

    this.sortedHeader = null;
    this.sortingOrder = 1;
    this.sortingFuncs = {
      'state' : (a, b) =>
          a['state'].localeCompare(b['state']) * this.sortingOrder,
      'margin' : (a, b) =>
          (Math.abs(a['margin']) - Math.abs(b['margin'])) * this.sortingOrder,
      'winstate_inc' : (a, b) =>
          (a['winstate_inc'] - b['winstate_inc']) * this.sortingOrder,
    };

    this.vizWidth = 300;
    this.vizHeight = 30;
    this.smallVizHeight = 20;

    this.scaleX =
        d3.scaleLinear().domain([ -100, 100 ]).range([ 0, this.vizWidth ]);

    this.attachSortHandlers();
    this.drawLegend();
  }

  drawLegend()
  {
    ////////////
    // PART 2 //
    ////////////
    /**
     * Draw the legend for the bar chart.
     */
    let legend = d3.select("#marginAxis");
    let biden = legend.append("g").attr("id", "marginAxisBiden");
    let separator = legend.append("g").attr("id", "marginAxisSeparator");
    let trump = legend.append("g").attr("id", "marginAxisTrump");

    biden.selectAll("text")
        .data([ '+75', '+50', '+25' ])
        .enter()
        .append('text')
        .classed('biden', true)
        .attr('x', (_d, i) => (i + 1) * (300 / 8) - 16)
        .attr('y', 18)
        .text(d => d)
        .style('text-align', 'center');

    this.addGridlines(separator, [ 0 ]);

    trump.selectAll("text")
        .data([ '+25', '+50', '+75' ])
        .enter()
        .append('text')
        .classed('trump', true)
        .attr('x', (_d, i) => (i + 5) * (300 / 8) - 16)
        .attr('y', 18)
        .text(d => d);
  }

  drawTable()
  {
    let rowSelection = d3.select('#predictionTableBody')
                           .selectAll('tr')
                           .data(this.tableData)
                           .join('tr');

    rowSelection.on('click', (event, d) => {
      if (d.isForecast)
      {
        this.toggleRow(d, this.tableData.indexOf(d));
      }
    });

    let forecastSelection = rowSelection.selectAll('td')
                                .data(this.rowToCellDataTransform)
                                .join('td')
                                .attr('class', d => d.class);

    ////////////
    // PART 1 //
    ////////////
    /**
     * with the forecastSelection you need to set the text based on the data
     * value as long as the type is 'text'
     */

    let textSelection = forecastSelection.filter(d => d.type === 'text');
    textSelection.html(d => `${d.value}${
                           d.isExpanded ? ' <i class="fas fa-caret-down"></i>'
                                        : ''}`);

    let vizSelection = forecastSelection.filter(d => d.type === 'viz');

    let svgSelect =
        vizSelection.selectAll('svg')
            .data(d => [d])
            .join('svg')
            .attr('width', this.vizWidth)
            .attr('height',
                  d => d.isForecast ? this.vizHeight : this.smallVizHeight);

    let grouperSelect = svgSelect.selectAll('g').data(d => [d, d, d]).join('g');

    this.addGridlines(grouperSelect.filter((d, i) => i === 0),
                      [ -75, -50, -25, 0, 25, 50, 75 ]);
    this.addRectangles(grouperSelect.filter((d, i) => i === 1));
    this.addCircles(grouperSelect.filter((d, i) => i === 2));
  }

  rowToCellDataTransform(d)
  {
    let stateInfo = {
      type : 'text',
      class : d.isForecast ? 'state-name' : 'poll-name',
      value : d.isForecast ? d.state : d.name
    };

    let marginInfo = {
      type : 'viz',
      value : {
        marginLow : +d.margin_lo,
        margin : +d.margin,
        marginHigh : +d.margin_hi,
      }
    };
    let winChance;
    if (d.isForecast)
    {
      const trumpWinChance = +d.winstate_inc;
      const bidenWinChance = +d.winstate_chal;

      const trumpWin = trumpWinChance > bidenWinChance;
      const winOddsValue = Math.max(trumpWinChance, bidenWinChance);
      let winOddsMessage;
      if (winOddsValue > 99)
        winOddsMessage = '> 99 in 100';
      else
        winOddsMessage = `${winOddsValue} in 100`
        winChance = {
          type : 'text',
          class : trumpWin ? 'trump' : 'biden',
          value : winOddsMessage
        }
    }
    else
    {
      winChance = { type : 'text', class : '', value : '' }
    }

    let dataList = [ stateInfo, marginInfo, winChance ];
    stateInfo.isExpanded = d.isExpanded;
    for (let point of dataList)
      point.isForecast = d.isForecast;

    return dataList;
  }

  updateHeaders(newSortedHeader)
  {
    ////////////
    // PART 6 //
    ////////////
    /**
     * update the column headers based on the sort state
     */

    let headers = d3.select('#columnHeaders');

    if (newSortedHeader != this.sortedHeader)
    {
      if (this.sortedHeader)
      {
        headers.select(`#${this.sortedHeader}`)
            .attr('class', 'sortable')
            .select('i')
            .attr('class', 'fas no-display');
      }

      this.sortedHeader = newSortedHeader;
      this.sortingOrder = -1;
    }

    this.sortingOrder *= -1;
    this.sortTableData();
    this.drawTable();

    headers.select(`#${newSortedHeader}`)
        .attr('class', 'sorting')
        .select('i')
        .attr('class',
              `fas fa-caret-${this.sortingOrder == 1 ? 'down' : 'up'}`);
  }

  sortTableData()
  {
    let compare = (a, b) => {
      if (a['state'] != b['state'])
      {
        let stateA = this.tableDataLookup.get(a['state']),
            stateB = this.tableDataLookup.get(b['state']);

        return this.sortingFuncs[this.sortedHeader](stateA, stateB);
      }

      let isAForecast = a.hasOwnProperty('cycle'),
          isBForeCast = b.hasOwnProperty('cycle');

      if (isAForecast ^ isBForeCast)
        return isAForecast ? -1 : 1;

      if (!isAForecast)
      {
        if (this.sortedHeader == 'state')
          return a['name'].localeCompare(b['name']) * this.sortingOrder;

        if (this.sortedHeader == 'winstate_inc')
          return (a['margin'] - b['margin']) * this.sortingOrder;
      }

      return this.sortingFuncs[this.sortedHeader](a, b);
    };

    this.tableData.sort(compare);
  }

  addGridlines(containerSelect, ticks)
  {
    ////////////
    // PART 3 //
    ////////////

    containerSelect.selectAll('line').remove();

    for (let tick of ticks)
    {
      let lineX = this.scaleX(tick);
      let lines = containerSelect.append('line')
                      .attr('x1', lineX)
                      .attr('y1', 0)
                      .attr('x2', lineX)
                      .attr('y2', 30);

      if (tick == 0)
        lines.style('stroke', '#707070');
      else if (tick < 0)
        lines.style('stroke', '#E0E0E0');
      else
        lines.style('stroke', '#E0E0E0');
    }
  }

  addRectangles(containerSelect)
  {
    ////////////
    // PART 4 //
    ////////////
    /**
     * add rectangles for the bar charts
     */

    let biden = containerSelect.selectAll('#rect-biden').data(d => [d]);
    biden = biden.enter().append('rect').attr('id', 'rect-biden').merge(biden);
    let trump = containerSelect.selectAll('#rect-trump').data(d => [d]);
    trump = trump.enter().append('rect').attr('id', 'rect-trump').merge(trump);

    const RECT_HEIGHT = 22;

    biden.classed('biden margin-bar', true)
        .attr('x', d => d.isForecast ? this.scaleX(d.value.marginLow) : 0)
        .attr('y', (30 - RECT_HEIGHT) / 2)
        .attr('width',
              d => d.isForecast
                       ? Math.max(0,
                                  this.scaleX(Math.min(d.value.marginHigh, 0)) -
                                      this.scaleX(d.value.marginLow))
                       : 0)
        .attr('height', RECT_HEIGHT);

    trump.classed('trump margin-bar', true)
        .attr('x', d => d.isForecast
                            ? this.scaleX(Math.max(d.value.marginLow, 0))
                            : 0)
        .attr('y', (30 - RECT_HEIGHT) / 2)
        .attr('width', d => d.isForecast
                                ? Math.max(0, this.scaleX(d.value.marginHigh) -
                                                  this.scaleX(Math.max(
                                                      d.value.marginLow, 0)))
                                : 0)
        .attr('height', RECT_HEIGHT);
  }

  addCircles(containerSelect)
  {
    ////////////
    // PART 5 //
    ////////////
    /**
     * add circles to the visualizations
     */

    let circles = containerSelect.selectAll('circle').data(d => [d]);
    circles = circles.enter().append('circle').merge(circles);
    circles
        .attr('class', d => `${d.value.margin < 0 ? 'biden' : 'trump'}${
                           d.isForecast ? ' forecast' : ''}`)
        .attr('cx', d => this.scaleX(d.value.margin))
        .attr('cy', d => d.isForecast ? 15 : 10)
        .attr('r', d => d.isForecast ? 5 : 4);
  }

  attachSortHandlers()
  {
    ////////////
    // PART 7 //
    ////////////
    /**
     * Attach click handlers to all the th elements inside the columnHeaders
     * row. The handler should sort based on that column and alternate between
     * ascending/descending.
     */

    let headers = d3.select('#columnHeaders');
    let table = this;

    headers.selectAll('th').on('click', function() {
      table.updateHeaders(d3.select(this).attr('id'));
    });
  }

  toggleRow(rowData, index)
  {
    ////////////
    // PART 8 //
    ////////////
    /**
     * Update table data with the poll data and redraw the table.
     */

    let pollData = this.pollData.get(rowData['state']);
    if (!pollData || !pollData.length)
      return;

    rowData.isExpanded = !rowData.isExpanded;
    if (rowData.isExpanded)
    {
      this.tableData.splice(index + 1, 0, ...pollData);
    }
    else
    {
      this.tableData.splice(index + 1, pollData.length);
    }

    if (!this.sortedHeader)
    {
      this.sortedHeader = 'margin';
      this.sortTableData();
      this.sortedHeader = null;
    }
    else
    {
      this.sortTableData();
    }

    this.drawTable();
  }

  collapseAll()
  {
    this.tableData = this.tableData.filter(d => d.isForecast);
  }
}
