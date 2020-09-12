package demos.others;

public class DAGGeneratorDemo
{
	public static void main(String[] args)
	{
		int MIN_PER_RANK = 3;/* Nodes/Rank: How 'fat' the DAG should be. */
		int MAX_PER_RANK = 3;
		int MIN_RANKS = 3; /* Ranks: How 'tall' the DAG should be. */
		int MAX_RANKS = 3;
		int PERCENT = 100; /* Chance of having an Edge. */
		
		
		int i, j, k, nodes = 0;
		
		int ranks = MIN_RANKS + (int)(Math.random() * (MAX_RANKS - MIN_RANKS + 1));
		
		System.out.println("digraph {");
		for (i = 0; i < ranks; i++)
		{
			/* New nodes of 'higher' rank than all nodes generated till now. */
			int new_nodes = MIN_PER_RANK + (int)(Math.random() * (MAX_PER_RANK - MIN_PER_RANK + 1));
			
			/* Edges from old nodes ('nodes') to new ones ('new_nodes'). */
			for (j = 0; j < nodes; j++)
				for (k = 0; k < new_nodes; k++)
					if (Math.random() * 100 < PERCENT)
						System.out.printf("  %d -> %d;\n", j, k + nodes); /* An Edge. */
						
			nodes += new_nodes; /* Accumulate into old node set. */
		}
		System.out.println("}\n");
		
	}
}
