package org.movies.database.environment;

import bitronix.tm.resource.jdbc.PoolingDataSource;

import java.util.Properties;

public enum DatabaseProduct {


    ORACLE(
        new DataSourceConfiguration() {
            @Override
            public void configure(PoolingDataSource ds, String connectionURL) {
                ds.setClassName("oracle.jdbc.xa.client.OracleXADataSource");
                ds.getDriverProperties().put(
                    "URL",
                    connectionURL != null
                        ? connectionURL :
                        "jdbc:oracle:thin:test/test@192.168.56.101:1521:xe"
                );

                // Required for reading VARBINARY/LONG RAW columns easily, see
                // http://stackoverflow.com/questions/10174951
                Properties connectionProperties = new Properties();
                connectionProperties.put("useFetchSizeWithLongColumn", "true");
                ds.getDriverProperties().put("connectionProperties", connectionProperties);
            }
        },
        org.hibernate.dialect.Oracle10gDialect.class.getName()
    ),

    POSTGRESQL(
        new DataSourceConfiguration() {
            @Override
            public void configure(PoolingDataSource ds, String connectionURL) {
                ds.setClassName("org.postgresql.xa.PGXADataSource");
                if (connectionURL != null) {
                    throw new IllegalArgumentException(
                        "PostgreSQL XADataSource doesn't support connection URLs"
                    );
                }
                ds.getDriverProperties().put("serverName", "localhost");
                ds.getDriverProperties().put("databaseName", "movies");
                ds.getDriverProperties().put("user", "postgres");
                ds.getDriverProperties().put("password", "23091982");
            }
        },
        org.hibernate.dialect.PostgreSQL82Dialect.class.getName()
    ),

    MYSQL(
        new DataSourceConfiguration() {
            @Override
            public void configure(PoolingDataSource ds, String connectionURL) {
                // TODO: MySQL XA support is completely broken, we use the BTM XA wrapper
                //ds.setClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
                ds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
                ds.getDriverProperties().put(
                    "url",
                    connectionURL != null
                        ? connectionURL :
                        "jdbc:mysql://localhost/test?sessionVariables=sql_mode='PIPES_AS_CONCAT'"
                );

                ds.getDriverProperties().put("driverClassName", "com.mysql.jdbc.Driver");
            }
        },
        // Yes, this should work with 5.6, no idea why Gail named it 5.7
        org.hibernate.dialect.MySQL57InnoDBDialect.class.getName()
    );

    public DataSourceConfiguration configuration;
    public String hibernateDialect;

    private DatabaseProduct(DataSourceConfiguration configuration,
                            String hibernateDialect) {
        this.configuration = configuration;
        this.hibernateDialect = hibernateDialect;
    }

    public interface DataSourceConfiguration {

        void configure(PoolingDataSource ds, String connectionURL);
    }

}
