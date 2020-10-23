/**
 * Main script.
 *
 * The State of the State of the States
 * Qianlang Chen
 * H 10/22/20
 */

// TODO: extra credit

let bubbleChart = new BubbleChart('bubble-chart');
let phraseTable = new PhraseTable('phrase-table');

d3.json('./data/words.json').then(rawData => {
  for (let i = 0; i < rawData.length; i++) rawData[i]['id'] = i;

  bubbleChart.setData(rawData);
  phraseTable.setData(rawData);

  bubbleChart.onSelectionChange = x => phraseTable.selectPhrases(x);
});
