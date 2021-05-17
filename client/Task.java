package client;

import com.beust.jcommander.Parameter;

public class Task {
    @Parameter(names = "-t", required = true)
    protected String type;

    @Parameter(names = "-i")
    protected String index;

    @Parameter(names = "-m")
    protected String value;

}
