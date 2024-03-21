<html>
<head>
<style>
header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;     
}
nav {
    line-height:30px;
    background-color:#eeeeee;
    height:300px;
    width:100px;
    float:left;
    padding:5px;        
}

section {
    float: left;
    width: calc(100% - 120px); /* Subtracting the width of the sidebar (nav) */
    padding: 5px;       
}

footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
    padding:5px;       
}
</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/jquery.dataTables.css">
<script>
$(document).ready(function() {
    // Initialize DataTable
    var table = $('#cardTable').DataTable({
                columnDefs: [{
                    targets: -1, // Target the last column (Quantity)
                    render: function(data, type, row, meta) {
                        // Render increment and decrement buttons and delete button
                        return '<button class="increment" data-row="' + meta.row + '">+</button>' +
                            '<span>' + data + '</span>' +
                            '<button class="decrement" data-row="' + meta.row + '">-</button>' +
                            '<button class="delete" data-row="' + meta.row + '">Delete</button>';
                            
                    }
                }]
            });
    
    // Make AJAX call to fetch data from servlet
    $.ajax({
        url: 'HomePageServlet', // Specify the URL of your servlet
        method: 'POST', // Use POST method to send data
        success: function(response) {
            // Iterate over the retrieved cards and add them to the table
            response.forEach(function(card) {
                table.row.add([
                    card.color,
                    card.type,
                    card.cmc,
                    card.name,
                    card.quantity
                ]).draw();
            });
        },
        error: function(xhr, status, error) {
            // Handle errors
            console.error(error);
        }
    });

    // Handle click events for increment and decrement buttons
    $('#cardTable tbody').on('click', 'button.increment', function() {
        var rowIdx = $(this).data('row');
        var rowData = table.row(rowIdx).data(); // Get the data of the row
        var cardName = rowData[3]; // Assuming the card name is in the 4th column
        var quantity = parseInt(table.cell(rowIdx, -1).data()) + 1; // Get current quantity and increment
        table.cell(rowIdx, -1).data(quantity).draw(false);
        updateQuantity(cardName, quantity);
    });

    $('#cardTable tbody').on('click', 'button.decrement', function() {
        var rowIdx = $(this).data('row');
        var rowData = table.row(rowIdx).data(); // Get the data of the row
        var cardName = rowData[3]; // Assuming the card name is in the 4th column
        var quantity = parseInt(table.cell(rowIdx, -1).data());
        if (quantity > 0) {
            quantity--; // Decrement only if quantity is greater than 0
            table.cell(rowIdx, -1).data(quantity).draw(false);
        }
        updateQuantity(cardName, quantity);
    });
    
    $('#cardTable tbody').on('click', 'button.delete', function() {
        var rowIdx = $(this).data('row');
        var rowData = table.row(rowIdx).data();
        var cardName = rowData[3]; // Assuming the card name is in the 4th column
        deleteCard(cardName);
        table.row($(this).parents('tr')).remove().draw(false); // Remove the row from the DataTable
    });

    // Function to delete the card
    function deleteCard(cardName) {
        $.ajax({
            url: 'DeleteCardServlet', // URL of your servlet for deleting a card
            method: 'POST',
            data: {
                cardName: cardName
            },
            success: function(response) {
                // Handle success response if needed
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
    }
    
    function updateQuantity(cardName, quantity) {
        $.ajax({
            url: 'UpdateQuantityServlet', // URL of your servlet for updating quantity
            method: 'POST',
            data: {
                cardName: cardName,
                quantity: quantity
            },
            success: function (response) {
                // Handle success response if needed
            },
            error: function (xhr, status, error) {
                // Handle errors
                console.error(error);
            }
        });
    }

});</script>
</head>
<body>
    <header>
        <h1>Card Library</h1>
    </header>
    <nav>
        <a href="/tech-ex/home.jsp">Home</a> <br>
		<a href="/tech-ex/insert.html">Insert Cards</a> <br>
    </nav>
    <section>
    <table id="cardTable" class="display">
        <thead>
            <tr>
                <th>Color</th>
                <th>Type</th>
                <th>CMC</th>
                <th>Name</th>
                <th>Quantity</th>
            </tr>
        </thead>
        <tbody>
            <!-- Table body will be populated dynamically -->
        </tbody>
    </table>
    </section>
<footer>
Josh Gryzen Tech Exercise
</footer>
</body>
</html>
