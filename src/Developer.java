public class Developer extends TeamMember {

	public Developer(int teamSize, String threadName, int sprintCount) {
		super(teamSize, threadName, sprintCount);
	}

	@Override
	public void run() {
		for (int i=1;i <= this.sprintCount; i++){
            System.out.println(threadName + " (sprint"+i+")");
            try {
            	operate();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
		System.out.println(threadName + " bitti...");
	}

	@Override
	public void operate() {

		// Veri taban�ndaki sprint_backlog tablosundan bir g�rev al�narak tamamlanacak.
		// Tamamlanan g�rev veri taban�ndaki board tablosuna yaz�lacak. 
		
		/* bu k�s�m sizin taraf�n�zdan implement edilecek */
		
	}

}
