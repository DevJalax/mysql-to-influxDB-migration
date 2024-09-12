@Service
public class TransactionConsumer {

    @Autowired
    private InfluxDBService influxDBService;

    @KafkaListener(topics = "dbserver1.transactions", groupId = "transactions_group")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
        
        // Parse message and extract transaction data (assuming JSON format)
        // Use your own parsing logic based on the actual message structure.
        JSONObject jsonObject = new JSONObject(message);
        String timestamp = jsonObject.getString("timestamp");
        double amount = jsonObject.getDouble("amount");
        String type = jsonObject.getString("type");

        // Send data to InfluxDB
        influxDBService.writeTransactionData(timestamp, amount, type);
    }
}
