
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="java.io.*" %>
<%
    //to get the content type information from JSP Request Header
    String contentType = request.getContentType();
    //here we are checking the content type is not equal to Null and as well as the passed data from mulitpart/form-data is greater than or equal to 0
    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
        DataInputStream in = new DataInputStream(request.getInputStream());
        //we are taking the length of Content type data
        int formDataLength = request.getContentLength();
        byte dataBytes[] = new byte[formDataLength];
        int byteRead = 0;
        int totalBytesRead = 0;
        //this loop converting the uploaded file into byte code
        while (totalBytesRead < formDataLength) {
            byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
            totalBytesRead += byteRead;
            }

        String file = new String(dataBytes);
        //for saving the file name
        String saveFile = file.substring(file.indexOf("filename=\"") + 10);
        //System.err.println(saveFile);
        
        saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
        saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
        //System.err.println(saveFile);
        int lastIndex = contentType.lastIndexOf("=");
        String boundary = contentType.substring(lastIndex + 1,contentType.length());
        int pos;
        //System.err.println(saveFile);
        //extracting the index of file 
        pos = file.indexOf("filename=\"");
        pos = file.indexOf("\n", pos) + 1;
        pos = file.indexOf("\n", pos) + 1;
        pos = file.indexOf("\n", pos) + 1;
        int boundaryLocation = file.indexOf(boundary, pos) - 4;
        int startPos = ((file.substring(0, pos)).getBytes()).length;
        int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;

        // creating a new file with the same name and writing the content in new file
        saveFile= System.getProperty("catalina.home")+"/webapps/ROOT/Processes/"+saveFile;
        FileOutputStream fileOut = new FileOutputStream(saveFile);
        fileOut.write(dataBytes, startPos, (endPos - startPos));
        fileOut.flush();
        fileOut.close();
        

        %>
        <head>
        <title>YAWL File Uploader</title>
    </head>


        <p>YAWL File Details</p>
    <form action="dispatcher?operation=uploadFile" method="post">
    <table>
        <tr><th>Name</th><td><input name="name" type="text" style="width: 200px"></td><th></th></tr>
        <tr><th>Author</th><td><input name="author" type="text" style="width: 200px"></td><th></th></tr>
        <input name="url" type="hidden" value = <%out.println(saveFile);%> type="text" style="width: 200px"></td><th></th></tr>
        <tr><th>Date Created</th><td><input name="created"  type="text" style="width: 200px"></td><th></th></tr>
        <tr><th>Description</th><td><input name="description" type="text" style="width: 200px"></td><th></th></tr>
        <input name="filesize" type="hidden" value = <%out.println(totalBytesRead);%> type="text" style="width: 200px"></td><th></th></tr>
      
        
        
      
        </table>
    <input type="submit" value="Submit" style="width: 60px"> <input type="reset" value="Reset" style="width: 60px">

    </form>
        
        <Br><table border="2"><tr><td><b>You have successfully upload the file by the name of:</b>
        <% out.println(saveFile); out.println(totalBytesRead); %></td></tr></table> <%
        }
    
 
%>

<html>
    

            

    </html>