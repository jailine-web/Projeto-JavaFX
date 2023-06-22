package db;

public class DbExcecaoDeIntegridade extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DbExcecaoDeIntegridade(String msg) {
		super(msg);
	}
	
}
