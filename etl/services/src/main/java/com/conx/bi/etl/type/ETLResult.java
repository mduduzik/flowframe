package com.conx.bi.etl.type;



/**
 * Enum of different runtime results with numeric codes and
 * string indetifications of results.<br>
 * 
 * The order of results is:
 * <ol>
 * <li>N_A (no result available; component just created)
 * <li>READY (component is ready to be executed; was initialized)
 * <li>RUNNING (component is being executed)
 * <li><ul>
 * <li>FINISHED_OK (component finished execution succesfully)
 * <li>ERROR (componet finished execution with error)
 * <li>ABORTED (component was aborted/interrupted)
 * </ul></li>
 * </ol>
 */
public enum ETLResult {
    
    N_A(3,"N/A", false),
    READY(2,"READY", false),
    RUNNING(1,"RUNNING", false),
    WAITING(4,"WAITING", false),
    FINISHED_OK(0,"FINISHED_OK", true),
    ERROR(-1,"ERROR", true),
    ABORTED(-2,"ABORTED", true),
    TIMEOUT(-3, "TIMEOUT", true);
    
    private final int code;
    private final String message;
    private boolean stop;
    
    ETLResult(int code, String message, boolean stop) {
		this.code = code;
		this.message = message;
		this.stop = stop;
	}
    
    public int code(){return code;}
    
    public String message(){return message;}
    
    public boolean isStop(){return stop;}
    
    /**
     * Converts string representation to the enum.
     * @param result
     * @return the enum.
     */
    public static ETLResult fromString(String result) {
    	if (result.equals(N_A.message())) {
    		return N_A;
    	}
    	return ETLResult.valueOf(result);
    }
    
}