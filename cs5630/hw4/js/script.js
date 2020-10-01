loadData().then(data => {
  // no country selected by default
  let activeCountry = null;
  // default activeYear is 2000
  let activeYear = '2000';
  let that = this;

  // ******* TODO: PART 3 *******
  /**
   * Calls the functions of the views that need to react to a newly
   * selected/highlighted country
   *
   * @param countryID the ID object for the newly selected country
   */
  function updateCountry(countryID)
  {
    that.activeCountry = countryID;

    // TODO - your code goes here
  }

  // ******* TODO: PART 3 *******

  /**
   *  Takes the specified activeYear from the range slider in the GapPlot view.
   *  It takes the value for the activeYear as the parameter. When the range
   * slider is dragged, we have to update the gap plot and the info box.
   *  @param year the new year we need to set to the other views
   */
  function updateYear(year)
  {
    // TODO - your code goes here -
  }

  // process the raw data and create the array of CountryData objects.
  let countries = {};
  for (let i = 0; i < data["population"].length; i++)
  {
    let countryID = data["population"][i].geo.toUpperCase();
    let country = new CountryData(data, i);

    countries[countryID] = country;
  }

  // create the world map.
  let map = new Map(countries, updateCountry);
  map.drawMap(activeYear); // draw at least the outlines now so the users
                           // don't need to stare at an empty space.

  // create and draw the scatterplot.
  let plot = new GapPlot(countries, updateCountry, updateYear, activeYear);
  plot.drawPlot();
  plot.updatePlot(activeYear, "fertilityRate", "lifeExpectancy", "population");

  // ...
  let infoBox = new InfoBox(data);

  // load map geo data and draw the world map when finished.
  d3.json("data/world.json").then(geoData => {
    let geoMapData = topojson.feature(geoData, geoData.objects.countries);
    for (let feature of geoMapData.features)
    {
      let countryID = feature.id;
      let country = countries[countryID];
      if (!country)
        country = countries[countryID] = new CountryData();

      country.type = feature.type;
      country.id = countryID;
      country.geometry = feature.geometry;
    }

    // draw the world map.
    map.drawMap(activeYear);
  });

  // This clears a selection by listening for a click
  document.addEventListener("click", function(e) {
    updateCountry(null);
  }, true);
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
