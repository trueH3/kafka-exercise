// mongo-init.js -> this script initiate single node replica after mongodb launch
const config = {
  _id: "rs0",
  members: [{ _id: 0, host: "localhost:27017" }]
};

try {
  rs.initiate(config);
  print("Replica set initialized.");
} catch (e) {
  print(e);
}
