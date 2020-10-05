// preGrouped = d3.json('./data/polls.json');
extraCredit = d3.csv('./data/polls.csv');

Promise.all([ d3.csv('./data/forecasts.csv'), extraCredit ]).then(data => {
  let forecastData = data[0];
  let pollData = data[1];

  /////////////////
  // EXTRA CREDIT//
  /////////////////
  /**
   * replace preGrouped with extraCredit and uncomment the line that defines
   * extraCredit. Then use d3.rollup to group the csv file on the fly.
   *
   * If you are not doing the extra credit, you do not need to change this file.
   */

  // rolledPollData = new Map(pollData);
  rolledPollData = d3.rollup(
      pollData,
      d => Array.from(
          d3.rollup(d, e => ({
                         'state' : e[0]['state'],
                         'name' : e[0]['pollster'],
                         'margin' : (() => {
                           let trumpAns = e.filter(f => f['answer'] == 'Trump'),
                               bidenAns = e.filter(f => f['answer'] == 'Biden'),
                               trumpLen = trumpAns.length || 1,
                               bidenLen = bidenAns.length || 1;

                           return d3.sum(trumpAns, f => f['pct']) / trumpLen -
                                  d3.sum(bidenAns, f => f['pct']) / bidenLen;
                         })(),
                       }),
                    e => e['pollster'])
              .values()),
      d => d['state']);
  let table = new Table(forecastData, rolledPollData);
  table.drawTable();
});
