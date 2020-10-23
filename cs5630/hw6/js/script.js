/**
 * Main script. (Extra credit included.)
 *
 * The State of the State of the States
 * Qianlang Chen
 * H 10/22/20
 */

let bubbleChart = new BubbleChart('bubble-chart');
let phraseTable = new PhraseTable('phrase-table');

bubbleChart.onSelectionChange = x => phraseTable.selectPhrases(x);

d3.csv('./data/words-without-force-positions.csv').then(rawData => {
  for (let i = 0; i < rawData.length; i++) {
    rawData[i]['id'] = i;
    rawData[i]['position'] = +rawData[i]['percent_of_r_speeches'] -
        rawData[i]['percent_of_d_speeches'];
  }

  bubbleChart.setData(rawData);
  phraseTable.setData(rawData);
});
