import { useEffect, useState } from "react";
import { getCustomers } from "./services/customerService";
import CustomerList from "./components/customerList";

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
      
      <CustomerList customers={customers} />
      
    </div>
  );
}

export default App;