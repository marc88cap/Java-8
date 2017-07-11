<h1>This is a simple file matching application with serialization</h1><h5>from Deitel Java 8 exercises</h5>

<p>(Summary)In commercial data processing, it’s common to have several files in each application system. In an accounts receivable system, for example, there’s generally a master file containing detailed information about each customer, such as the customer’s name, address, telephone number, outstanding balance, credit limit, discount terms, contract arrangements and possibly a condensed history of recent purchases and cash payments.
As transactions occur (i.e., sales are made and payments arrive in the mail), information about them is entered into a file. At the end of each business period (a month for some companies, a week for others, and a day in some cases), the file of transactions (called "trans.txt") is applied to the master file (called "oldmast.txt") to update each account’s purchase and payment record.
During an update, the master file is rewritten as the file "newmast.txt", which is then used at the end of the next business period to begin the updating process again.
File-matching programs must deal with certain problems that do not arise in single-file programs. For example, a match does not always occur. If a customer on the master file has not made
any purchases or cash payments in the current business period, no record for this customer will appear on the transaction file. Similarly, a customer who did make some purchases or cash payments could have just moved to this community, and if so, the company may not have had a chance to create a master record for this customer.
</p>
<p>What is serialization? Serialization means that whole objects with data types are stored into a file. Which enables us to get back all input data with its types after a deserialization process.</p>
<p>With this application we generate data with manual input:</p>
<img src="http://i63.tinypic.com/aebaqu.png" alt="generate_data"/>

<p>When the data is generated or written to a file we can use that data to combine and store it to a new file:</p>
<img src="http://i66.tinypic.com/2md46yr.png" alt="match_files"/>
<p>In this example its assumed that negative transactions are purchases and positive transactions are payments.</p>

<p>After program finishes its work: files have been read("oldmast" and "intrans") and files have been combined into one file named "newmast". Also, a "log" file has been created.</p>
<img src="http://i63.tinypic.com/2ex0wzm.png" alt="files_created"/>

<p>In a "log" file, IDs of non-matching transactions with "oldmast" file have been stored.</p>
<img src="http://i63.tinypic.com/2upsnyo.png" alt="log_file_content"/>
</p>
