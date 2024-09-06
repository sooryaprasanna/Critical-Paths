Implemented the critical path method (CPM) algorithm in PERT charts to schedule a set of project activities.

**CPM and PERT**
- Critical Path Analysis can make the difference between success and failure on complex projects. It can be very useful for assessing the importance of problems faced during the implementation of the plan.
- PERT is a variant of Critical Path Analysis that takes a more skeptical view of the time needed to complete each project stage.

**Ideation**
- Critical Path Analysis and PERT are powerful tools that help you to schedule and manage complex projects.
- A list of all activities required to complete the project (typically categorized within a work breakdown structure)
    - The time (duration) that each activity will take to complete,
    - The dependencies between the activities,
    - Logical end points such as milestones or deliverable items.
- Using these values, CPM calculates the longest path of planned activities to logical end points or to the end of the project, and the earliest and latest that each activity can start and finish without making the project longer.
- This process determines which activities are "critical" (i.e., on the longest path) and which have "total float" (i.e., can be delayed without making the project longer).
- In project management, a critical path is the sequence of project network activities which add up to the longest overall duration, regardless if that longest duration has float or not.
- This determines the shortest time possible to complete the project.

**Design**
1. To find the topological order of the graph, perform DFS from a source node.
2. Initially all the nodes will be marked white denoting it’s not traversed.
3. The method dfsVisit (Graph g,Vertex u) is implemented to check whether the graph is visited or not.
4. The color is changed to Gray when the node is being visited, and marked black when the node is visited.
5. Add this to a List of Vertices that in turn gives us the topological order.
6. Enumerate all paths in a DAG from start to end enumeratePaths (Vertex u, index).
7. HashMap is maintained for all paths along with the index.
8. EC is calculated by comparing all the incoming edges to the vertex v and by taking the maximum value of all incoming edges.
9. LC is calculated by comparing all the incoming edges to the vertex v and take the minimum value of all incoming edges for which, we need to reverse the topological order to traverse back
10. For every edge present in the path, check for the critical edge (lc = ec) and add them to array of vertices.
11. Iterator over them to find all critical paths, its length and print them.
