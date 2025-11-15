package core.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RootDbConfig {
    @JsonProperty("connection_details")
    private List<DbConfig> connectionDetails;


    @Data
    public static class DbConfig {
        @JsonProperty("hostname")
        private String hostName;
        @JsonProperty("dbname")
        private String dbName;
        @JsonProperty("portnumber")
        private String portNumber;
        @JsonProperty("username")
        private String username;
        @JsonProperty("password")
        private String password;
        @JsonProperty("connectionmethod")
        private String connectionMethod;
        @JsonProperty("dbid")
        private String dbId;
        @JsonProperty("dbtype")
        private String dbType;
    }
}
