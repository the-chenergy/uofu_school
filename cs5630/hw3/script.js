//#region DATA LOADING /////////////////////////////////////////////////////////

/**
 * Update the data according to document settings
 */
async function changeData()
{
  //  Load the file indicated by the select menu
  let dataFile = document.getElementById("dataset").value;
  try
  {
    const data = await d3.csv("data/" + dataFile + ".csv");

    /* D3 loads all CSV data as strings. While Javascript is pretty smart
     * about interpreting strings as numbers when you do things like
     * multiplication, it will still treat them as strings where it makes
     * sense (e.g. adding strings will concatenate them, not add the values
     * together, or comparing strings will do string comparison, not numeric
     * comparison).
     *
     * We need to explicitly convert values to numbers so that comparisons work
     * when we call d3.max() */
    for (let d of data)
    {
      d.cases = +d.cases;   // unary operator converts string to number
      d.deaths = +d.deaths; // unary operator converts string to number
    }

    if (document.getElementById("random").checked)
    {
      // if random
      update(randomSubset(data)); // update w/ random subset of data
    }
    else
    {
      // else
      update(data); // update w/ full data
    }
  }
  catch (error)
  {
    console.log(error);
    alert("Could not load the dataset!\n\n" + error);
  }
}

/**
 *  Slice out a random chunk of the provided in data
 *  @param data
 */
function randomSubset(data)
{
  return data.filter(_d => Math.random() > 0.5);
}

//#endregion

//#region DATA UPDATING ////////////////////////////////////////////////////////

/* PS: I hate how this code was (and still is) filled of magic constants, making
 * re-styling extremely irritating. Like ew! */
const BAR_CHART_WIDTH = 345, MAX_BAR_WIDTH = 240, BAR_HEIGHT = 12,
      BAR_PADDING = 2;
const LINE_CHART_WIDTH = 540;
const AREA_CHART_WIDTH = 295, MAX_AREA_WIDTH = 260;
const SCATTER_PLOT_DOT_RADIUS = 6;

/** Render the visualizations. */
function update(data)
{
  // extract cases and deaths numbers from the raw data.
  let aData = data.map(d => d.cases), bData = data.map(d => d.deaths);

  // set up the scales for normalizing the data into locations on screen.
  let aDataScale = linearScale(Math.max(...aData), 0, MAX_BAR_WIDTH);
  let bDataScale = linearScale(Math.max(...bData), 0, MAX_BAR_WIDTH);

  // draw the axes for the charts.
  drawAxes(data, aDataScale, bDataScale);

  // draw the charts.
  drawBarChart("#aBarChart", aData, aDataScale);
  drawBarChart("#bBarChart", bData, bDataScale);

  let lineChartXScale = linearScale(data.length, 10, LINE_CHART_WIDTH - 20);
  drawLineChart("#aLineChart", aData, lineChartXScale, aDataScale);
  drawLineChart("#bLineChart", bData, lineChartXScale, bDataScale);

  let areaChartXScale = linearScale(data.length, 0, MAX_AREA_WIDTH);
  drawAreaChart("#aAreaChart", aData, areaChartXScale, aDataScale);
  drawAreaChart("#bAreaChart", bData, areaChartXScale, bDataScale);

  drawScatterPlot("#scatterplot", bData, aData, bDataScale, aDataScale);

  // ****** TODO: PART IV ******
}

function linearScale(maxValue, mapToLow, mapToHigh)
{
  return d3.scaleLinear().domain([ 0, maxValue ]).range([
    mapToLow, mapToHigh
  ]);
}

function drawAxes(data, aDataScale, bDataScale)
{
  /* This could still be refactored to be more maintainable, but good enough for
   * now... */
  d3.select("#aBarChart-axis")
      .attr("transform", "translate(0,210)")
      .call(d3.axisBottom(
                  d3.scaleLinear()
                      .domain([ 0, d3.max(data, d => d.cases) ])
                      .range(
                          [ BAR_CHART_WIDTH, BAR_CHART_WIDTH - MAX_BAR_WIDTH ]))
                .ticks(5));
  d3.select("#aAreaChart-axis")
      .attr("transform", "translate(0,245)")
      .call(
          d3.axisBottom(
                d3.scaleLinear()
                    .domain([ 0, d3.max(data, d => d.cases) ])
                    .range(
                        [ AREA_CHART_WIDTH, AREA_CHART_WIDTH - MAX_BAR_WIDTH ]))
              .ticks(5));
  d3.select("#bBarChart-axis")
      .attr("transform", "translate(5,210)")
      .call(d3.axisBottom(bDataScale).ticks(5));
  d3.select("#bAreaChart-axis")
      .attr("transform", "translate(5,245)")
      .call(d3.axisBottom(bDataScale).ticks(5));
  let aAxis_line = d3.axisLeft(aDataScale).ticks(5);
  d3.select("#aLineChart-axis")
      .attr("transform", "translate(50,15)")
      .call(aAxis_line);
  d3.select("#aLineChart-axis")
      .append("text")
      .text("New Cases")
      .attr("transform", "translate(50, -3)")
  let bAxis_line = d3.axisRight(bDataScale).ticks(5);
  d3.select("#bLineChart-axis")
      .attr("transform", "translate(550,15)")
      .call(bAxis_line);
  d3.select("#bLineChart-axis")
      .append("text")
      .text("New Deaths")
      .attr("transform", "translate(-50, -3)");

  // scatter plot
  let scat = d3.select("#scatterplot-axes");
  let aDataScaleRev = linearScale(d3.max(data, d => d.cases), MAX_BAR_WIDTH, 0);
  scat.select("#y-axis").call(d3.axisLeft(aDataScaleRev).ticks(5));
  scat.append("text")
      .text("New Cases")
      .attr("transform", "scale(0.6667, 0.6667)");
  scat.select("#x-axis").call(d3.axisBottom(bDataScale).ticks(5));
  scat.append("text")
      .text("New Deaths")
      .attr("transform", "translate(" + MAX_BAR_WIDTH + ", " + MAX_BAR_WIDTH +
                             ") scale(0.6667, 0.6667)");
  scat.select("#scatterplot")
      .attr("transform", "translate(0, " + MAX_BAR_WIDTH + ") scale(1, -1)");
}

function drawBarChart(element, data, widthScale)
{
  // generate transform settings string
  let tf = y => "translate(0, " + y * (BAR_HEIGHT + BAR_PADDING) + ")";

  let bars = d3.select(element).selectAll("rect").data(data);
  bars = bars.enter().append("rect").merge(bars);
  bars.attr("transform", (_d, i) => tf(i))
      .attr("width", d => widthScale(d))
      .attr("height", BAR_HEIGHT);
}

function drawLineChart(element, data, xScale, yScale)
{
  // line generator
  let gen = d3.line().x((_d, i) => xScale(i)).y(d => yScale(d));
  d3.select(element).attr("d", gen(data));
}

function drawAreaChart(element, data, xScale, yScale)
{
  // area generator
  let gen = d3.area().x((_d, i) => xScale(i)).y0(0).y1(d => yScale(d));
  d3.select(element).attr("d", gen(data));
}

function drawScatterPlot(element, xData, yData, xScale, yScale)
{
  let data = xData.map((d, i) => [d, yData[i]]);
  let tf = d => "translate(" + xScale(d[0]) + ", " + yScale(d[1]) + ")";

  let dots = d3.select(element).selectAll("circle").data(data);
  dots = dots.enter().append("circle").merge(dots);
  dots.attr("transform", d => tf(d)).attr("r", SCATTER_PLOT_DOT_RADIUS);
}

//#endregion

//#region OTHER FEATURES ///////////////////////////////////////////////////////
/**
 * Makes the first bar chart appear as a staircase.
 *
 * Note: use only the DOM API, not D3!
 */
function staircase()
{
  let barChart = document.getElementById("aBarChart");
  let rects = barChart.getElementsByTagName("rect");

  let barLengths = [];
  for (let rect of rects)
    barLengths.push(+rect.getAttribute("width"));
  barLengths.sort((i, j) => i - j);
  for (let i = 0; i < rects.length; i++)
    rects[i].setAttribute("width", barLengths[i]);
}

//#endregion
