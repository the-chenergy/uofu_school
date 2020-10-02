/** Data structure for the data associated with an individual country. */
class InfoBoxData
{
  constructor(data, indices)
  {
    this.data = {};
    for (let indicator in data)
      this.data[indicator] = data[indicator][indices[indicator]];

    this.name = data["gdp"][indices["gdp"]]["country"];
    this.id = data["gdp"][indices["gdp"]]["geo"];
    this.region = "countries";
    if (indices["population"] != -1)
      this.region = data["population"][indices["population"]]["region"];
  }
}

/** Class representing the highlighting and selection interactivity. */
class InfoBox
{
  /**
   * Creates a InfoBox Object
   * @param data the full data array
   */
  constructor(data)
  {
    // initialize country data.
    this.indicators = {};
    for (let indicator in data)
      this.indicators[indicator] = data[indicator][0]["indicator_name"];

    let countryIndices = {};
    for (let indicator in data)
    {
      for (let i in data[indicator])
      {
        let country = data[indicator][i];
        let id = country["geo"];
        let indices = countryIndices[id];
        if (!indices)
        {
          indices = {};
          for (let j in this.indicators)
            indices[j] = -1;

          countryIndices[id] = indices;
        }

        indices[indicator] = i;
      }
    }

    this.countries = {};
    for (let i in data["gdp"])
    {
      let id = data["gdp"][i]["geo"];
      this.countries[id] = new InfoBoxData(data, countryIndices[id]);
    }

    // create the text elements needed.
    let panel = d3.select("#country-detail");

    panel.append("div").attr("class", "label").attr("id", "info-panel-label");
    for (let indicator in data)
      panel.append("div")
          .attr("class", "stats")
          .attr("id", "info-panel-stats-" + indicator);
    panel.style("display", "none");
  }

  /**
   * Renders the country description
   * @param activeCountry the IDs for the active country
   * @param activeYear the year to render the data for
   */
  updateTextDescription(activeCountry, activeYear)
  {
    // Update the text elements in the infoBox to reflect:
    // Selected country, region, population and stats associated with the
    // country.
    /*
     * You will need to get an array of the values for each category in your
     * data object hint: you can do this by using Object.values(this.data) you
     * will then need to filter just the activeCountry data from each array you
     * will then pass the data as parameters to make an InfoBoxData object for
     * each category
     *
     */

    let panel = d3.select("#country-detail");
    let country = this.countries[activeCountry];

    panel.style("display", "inline-block");
    panel.select("#info-panel-label")
        .html("<i class=\"fas fa-globe-asia " + country.region + "\"></i> " +
              country.name);

    let data = [];
    for (let indicator in country.data)
    {
      let title = this.indicators[indicator];
      title = title[0].toUpperCase() + title.substring(1);

      let value = "no data";
      let stats = country.data[indicator];
      if (stats)
        value = d3.format(",")(stats[activeYear]);

      data.push(title + ": <span>" + value + "</span>");
    }

    panel.selectAll("[id^=info-panel-stats-]").data(data).html(d => d);
  }

  /**
   * Removes or makes invisible the info box
   */
  clearHighlight()
  {
    d3.select("#country-detail").style("display", "none");
  }
}