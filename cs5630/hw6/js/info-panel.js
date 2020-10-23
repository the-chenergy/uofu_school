/**
 * Controls the information panel element in the webpage.
 *
 * The State of the State of the States
 * Qianlang Chen
 * T 10/20/20
 */
class InfoPanel {
  //#region STATIC MEMBERS /////////////////////////////////////////////////////

  //#endregion

  //#region CONSTRUCTOR AND INITIALIZATIONS ////////////////////////////////////

  /**
   * Creates a new InfoPanel instance.
   * @param {string} elementId The name of the HTML element which the info panel
   *     be built on.
   */
  constructor(elementId) {
    /**
     * @private @type {Selection} The selection containing the info panel
     *     element.
     */
    this.element = d3.select('#' + elementId);

    this.element.attr('class', 'info-panel');
  }

  //#endregion

  //#region METHODS ////////////////////////////////////////////////////////////

  /**
   * Shows the info panel.
   * @param {number} x The X location to show the info panel.
   * @param {number} y The Y location to show the info panel.
   * @param {string} html The HTML contents to show.
   */
  show(x, y, html) {
    this.element.html(html);
    this.move(x, y);
  }

  /**
   * Moves the info panel while keeping the contents.
   * @param {number} x The X location to move the info panel to.
   * @param {number} y The Y location to move the info panel to.
   */
  move(x, y) {
    let isShowing = this.element.style('opacity') > 0;
    this.element.transition()
        .duration(0)
        .style('opacity', 1)
        .duration(isShowing ? 65 : 0)
        .ease(d3.easeSinOut)
        .style('left', x + 'px')
        .style('top', y + 'px');
  }

  /** Hides the info panel. */
  hide() {
    this.element.transition()
        .duration(174)
        .ease(d3.easeSinIn)
        .style('opacity', 0);
  }

  //#endregion
}
