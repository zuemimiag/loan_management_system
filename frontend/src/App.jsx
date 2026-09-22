import { useEffect, useState } from "react";
import { getCustomers } from "./services/customerService";

function App() {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    const loadCustomers = async () => {
      const data = await getCustomers();
      setCustomers(data);
    };

    loadCustomers();
  }, []);

  return (
    <div>
      <h1>Smart Loan Management System</h1>

      <h2>Customers</h2>

      {customers.map((customer) => (
        <div key={customer.id}>
          <p>Name: {customer.customerName}</p>
          <p>Phone: {customer.phone}</p>
          <p>Address: {customer.address}</p>
          <hr />
        </div>
      ))}
    </div>
  );
}

export default App;