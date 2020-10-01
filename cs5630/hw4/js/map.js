/**
 * Data structure for the data associated with an individual country.
 * the CountryData class will be used to keep the data for drawing your map.
 * You will use the region to assign a class to color the map!
 */
class CountryData
{
  /**
   * Creates a new CountryData object.
   *
   * @param  rawStatsData  The raw data objects contains population, etc.
   * @param  i             The index in the rawStatsData to access the data for
   *                       this country.
   */
  constructor(rawStatsData = null, i = -1)
  {
    if (!rawStatsData)
    {
      this.hasStats = false;
      this.region = "countries";
      return;
    }

    this.hasStats = true;
    this.region = rawStatsData["population"][i].region;
    this.id = rawStatsData["population"][i].geo.toUpperCase();
    this.population = rawStatsData["population"][i];
    this.gdp = rawStatsData["gdp"][i];
    this.childMortality = rawStatsData["child-mortality"][i];
    this.lifeExpectancy = rawStatsData["life-expectancy"][i];
    this.fertilityRate = rawStatsData["fertility-rate"][i];
  }
}

/** Class representing the map view. */
class Map
{
  /**
   * Creates a Map Object.
   *
   * @param  countries      The array of CountryData objects.
   * @param  updateCountry  A callback function used to notify other parts of
   *                        the program when the selected country was updated
   *                        (clicked).
   */
  constructor(countries, updateCountry)
  {
    this.projection = d3.geoWinkel3().scale(140).translate([ 365, 225 ]);
    this.updateCountry = updateCountry;
    this.countries = countries;
  }

  /**
   * Renders the map
   *
   * @param  activeYear  String for the activeYear
   */
  drawMap(activeYear)
  {
    // note that projection is global!

    // ******* TODO: PART I *******

    // Draw the background (country outlines; hint: use #map-chart)
    // Make sure to add a graticule (gridlines) and an outline to the map

    // Hint: assign an id to each country path to make it easier to select
    // afterwards we suggest you use the variable in the data element's id field
    // to set the id

    // Make sure and give your paths the appropriate class (see the .css
    // selectors at the top of the provided html file)

    // You need to match the country with the region. This can be done using
    // .map() We have provided a class structure for the data called CountryData
    // that you should assign the parameters to in your mapping

    // draw countries
    let chart = d3.select("#map-chart");
    let countryFeatures = Object.values(this.countries);
    let countryPaths = chart.selectAll("path").data(countryFeatures);
    countryPaths.exit().remove();
    countryPaths = countryPaths.enter().append("path").merge(countryPaths);
    let geoPath = d3.geoPath().projection(this.projection);
    countryPaths.attr("class", country => country.region)
        .attr("id", country => "country-path-" + country.id)
        .attr("d", geoPath);

    // draw grids
    let geoGraticule = d3.geoGraticule();
    let gridPath = chart.append("path").datum(geoGraticule);
    gridPath.attr("class", "graticule").attr("d", geoPath);
    let gridBoundaryPath = chart.append("path").datum(geoGraticule.outline());
    gridBoundaryPath.attr("class", "stroke").attr("d", geoPath);
  }

  /**
   * Highlights the selected country and region on mouse click
   * @param activeCountry the country ID of the country to be rendered as
   *     selected/highlighted
   */
  updateHighlightClick(activeCountry)
  {
    // ******* TODO: PART 3 *******
    // Assign selected class to the target country and corresponding region
    // Hint: If you followed our suggestion of using classes to style
    // the colors and markers for countries/regions, you can use
    // d3 selection and .classed to set these classes on here.

    // TODO - your code goes here
  }

  /**
   * Clears all highlights
   */
  clearHighlight()
  {
    // ******* TODO: PART 3 *******
    // Clear the map of any colors/markers; You can do this with inline styling
    // or by defining a class style in styles.css

    // Hint: If you followed our suggestion of using classes to style
    // the colors and markers for hosts/teams/winners, you can use
    // d3 selection and .classed to set these classes off here.

    // TODO - your code goes here
  }
}
