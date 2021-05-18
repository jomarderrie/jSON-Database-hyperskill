package client;

import com.beust.jcommander.Parameter;

public class Task {

    @Parameter(names = "-t", required = true)
    protected String type;

    @Parameter(names = "-k")
    protected String key;

    @Parameter(names = "-v")
    protected String value;


}
