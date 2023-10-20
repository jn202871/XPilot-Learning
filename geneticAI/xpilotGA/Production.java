class Production extends javaAI {
	public static double fitness = 0;
	public static String chromosome;
	int round = 0;
	boolean alive = (selfAlive() == 1 ? true : false);
        @Override
        public void AI_loop() {
			if (round == 6) {
				fitness = selfScore();
				quitAI();
			}
			
			// Chromosome Interpretation
			// Production System
			
			if ((selfAlive() == 1 ? true : false) != alive) {
				round++;
				alive = (selfAlive() == 1 ? true : false);
			} 
        }
        public Production(String args[]) {
        	super(args);
        }
        public static void main(String args[]) {
            String[] new_args = {"-name","Petko & Co.","-join","localhost"};
            Production production = new Production(new_args);
        }
}
