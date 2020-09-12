/**
 * Class representing a Tree.
 *
 * @author Qianlang Chen
 */
class Tree
{
  ////// CONSTRUCTOR ///////////////////////////////////////////////////////////

  /**
   * Creates a Tree Object
   * Populates a single attribute that contains a list (array) of Node objects
   * to be used by the other functions in this class note: Node objects will
   * have a name, parentNode, parentName, children, level, and position
   * @param {json[]} json - array of json objects with name and parent fields
   */
  constructor(json)
  {
    for (let data of json)
    {
      let node = new Node(data.name, data.parent);
      this.nodeArray.push(node);
      this.nodes.set(data.name, node);
    }
    // each map entry now has node_name -> node_ref.
  }

  ////// FIELDS ////////////////////////////////////////////////////////////////

  /**
   * The array of nodes, as required to be created by the constructor. (Why use
   * an array when a map fits better for this problem?)
   */
  nodeArray = [];

  /**
   * The collection of nodes, with each node's name mapped to a reference to a
   * `Node` object.
   */
  nodes = new Map();

  /** The total number of levels in this tree. */
  numLevels = 0;

  /** The total number of positions in this tree. */
  numPositions = 0;

  ////// BUILDING //////////////////////////////////////////////////////////////

  /**
   * Function that builds a tree from a list of nodes with parent refs
   */
  buildTree()
  {
    let roots = []; // in case there are multiple root nodes
    for (let node of this.nodes.values())
    {
      if (node.parentName == "root")
      {
        roots.push(node);
        continue;
      }
      node.parentNode = this.nodes.get(node.parentName);
      node.parentNode.addChild(node);
    }
    // references of the parent and children for each node are now correctly
    // set.

    let temp = 0;
    for (let i = 0; i < roots.length; i++)
    {
      this.assignLevel(roots[i], 0);
      temp = this.assignPosition(roots[i], temp);
    }
  }

  /**
   * Recursive function that assign levels to each node
   */
  assignLevel(node, level)
  {
    node.level = level;
    this.numLevels = Math.max(this.numLevels, level + 1);

    for (let child of node.children)
      this.assignLevel(child, level + 1);
  }

  /**
   * Recursive function that assign positions to each node
   */
  assignPosition(node, position)
  {
    node.position = position;
    this.numPositions = Math.max(this.numPositions, position + 1);

    let temp = 0;
    for (let child of node.children)
    {
      position = this.assignPosition(child, position + temp);
      temp = 1;
    }

    return position;
  }

  ////// RENDERING /////////////////////////////////////////////////////////////

  /** The width for each level when being rendered. */
  static levelWidth = 168;

  /** The height for each position when being rendered. */
  static positionHeight = 108;

  /** The radius of each node when being rendered. */
  static nodeRadius = 48;

  /**
   * Function that renders the tree
   */
  renderTree()
  {
    // print author info
    d3.select("body").append("div").text("Qianlang Chen");
    d3.select("body").append("div").text("qianlangchen@gmail.com");
    d3.select("body").append("div").text("u1172983");

    // "adaptive" graph size
    let graphWidth = Tree.levelWidth * this.numLevels;
    let graphHeight = Tree.positionHeight * this.numPositions;

    // initialize "canvas"
    let graph = d3.select("body").append("svg");
    graph.attr("width", graphWidth).attr("height", graphHeight);

    // draw lines
    graph.selectAll("line")
        .data(Array.from(this.nodes.values())
                  .filter(d => d.parentNode) // filter only nodes with parents
                  .map(d => [d.parentName, d.name]))
        .enter()
        .append("line")
        .attr("x1", d => this.getNodeX(d[0]))
        .attr("y1", d => this.getNodeY(d[0]))
        .attr("x2", d => this.getNodeX(d[1]))
        .attr("y2", d => this.getNodeY(d[1]));

    // draw nodes
    let nodeGroups =
        graph.selectAll("g")
            .data(Array.from(this.nodes.keys()))
            .enter()
            .append("g")
            .attr("class", "nodeGroup")
            .attr("transform", d => this.getTransformAttrString(d));

    nodeGroups.append("circle").attr("r", d => this.getNodeRadius(d));
    nodeGroups.append("text")
        .attr("class", "label")
        .attr("y", 4) // fix minor alignment error
        .text(d => d);
  }

  /** Returns the X location of a node when being rendered given its name.*/
  getNodeX(nodeName)
  {
    let node = this.nodes.get(nodeName);
    return node.level * Tree.levelWidth + Tree.positionHeight / 2;
  }

  /** Returns the Y location of a node when being rendered given its name.*/
  getNodeY(nodeName)
  {
    let node = this.nodes.get(nodeName);
    return node.position * Tree.positionHeight + Tree.positionHeight / 2;
  }

  /** Returns the attribute setting string for a node when transforming. */
  getTransformAttrString(nodeName)
  {
    return "translate(" + this.getNodeX(nodeName) + ", " +
           this.getNodeY(nodeName) + ")";
  }

  /** Returns the radius of a node when being rendered given its name.*/
  getNodeRadius(nodeName)
  {
    return Tree.nodeRadius;
  }
}