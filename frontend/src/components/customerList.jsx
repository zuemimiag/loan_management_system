function CustomerList({customers}){
    return(
        <div>
            <h2>Customers</h2>

            {customers.map((customer) => (
                <div key = {customer.id}>
                    <p>Name: {customer.customerName}</p>
                    <p>Phone:{customer.phone}</p>
                    <p>Address: {customer.address}</p>
                    <hr />
                    </div>
            ))}
        </div>
    );
}

export default CustomerList;