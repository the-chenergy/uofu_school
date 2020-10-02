loadData().then(data => {
  let activeCountry = null;
  let activeYear = '2000';

  // create the world map.
  let map = new Map(data, updateCountry);

  // create and draw the scatterplot.
  let plot = new GapPlot(data, updateCountry, updateYear, activeYear);
  plot.drawPlot();
  plot.updatePlot(activeYear, "life-expectancy", "gdp", "population");
  // update once now so the users doesn't have to choose to
  // be sure that our website is indeed running.

  // ...
  let infoBox = new InfoBox(data);

  // load map geo data and draw the world map when finished.
  d3.json("data/world.json").then(topoData => {
    let geoData = topojson.feature(topoData, topoData.objects.countries);
    map.drawMap(geoData, activeYear);
  });

  /**
   * Calls the functions of the views that need to react to a newly
   * selected/highlighted country
   *
   * @param countryID the ID object for the newly selected country
   */
  function updateCountry(countryID)
  {
    activeCountry = countryID;

    if (countryID)
    {
      map.updateHighlightClick(countryID);
      plot.updateHighlightClick(countryID);
      infoBox.updateTextDescription(countryID, activeYear);
    }
    else
    {
      map.clearHighlight();
      plot.clearHighlight();
      infoBox.clearHighlight();
    }
  }

  /**
   *  Takes the specified activeYear from the range slider in the GapPlot
   * view. It takes the value for the activeYear as the parameter. When the
   * range slider is dragged, we have to update the gap plot and the info box.
   *  @param year the new year we need to set to the other views
   */
  function updateYear(year)
  {
    activeYear = year;

    if (this.activeCountry)
      infoBox.updateTextDescription(this.activeCountry, year);
  }

  // This clears a selection by listening for a click
  let exemptFromDeactivation =
      new Set().add("slider").add("label").add("stats");

  function onDocumentClick(e)
  {
    if (exemptFromDeactivation.has(d3.select(e.target).attr("class")))
      return;

    updateCountry(null);
  }

  document.addEventListener("click", onDocumentClick, true);
});

// ******* DATA LOADING *******

/**
 * A file loading function or CSVs
 * @param file
 * @returns {Promise<T>}
 */
async function loadFile(file)
{
  let data = await d3.csv(file).then(d => {
    let mapped = d.map(g => {
      for (let key in g)
      {
        let numKey = +key;
        if (numKey)
        {
          g[key] = +g[key];
        }
      }
      return g;
    });
    return mapped;
  });
  return data;
}

async function loadData()
{
  let pop = await loadFile('data/pop.csv');
  let gdp = await loadFile('data/gdppc.csv');
  let tfr = await loadFile('data/tfr.csv');
  let cmu = await loadFile('data/cmu5.csv');
  let life = await loadFile('data/life_expect.csv');

  return {
    'population' : pop,
    'gdp' : gdp,
    'child-mortality' : cmu,
    'life-expectancy' : life,
    'fertility-rate' : tfr
  };
}
