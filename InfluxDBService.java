@Service
public class InfluxDBService {

    private final String bucket = "transactions";
    private final String org = "my_org";
    private final String token = "my_token";
    private final String influxDBUrl = "http://localhost:8086";

    private final InfluxDBClient influxDBClient;

    public InfluxDBService() {
        influxDBClient = InfluxDBClientFactory.create(influxDBUrl, token.toCharArray());
    }

    public void writeTransactionData(String timestamp, double amount, String type) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        // Prepare a point to write to InfluxDB
        Point point = Point
                .measurement("transactions")
                .addTag("type", type)
                .addField("amount", amount)
                .time(Instant.parse(timestamp), WritePrecision.NS);

        // Write point to InfluxDB
        writeApi.writePoint(bucket, org, point);
    }
}
