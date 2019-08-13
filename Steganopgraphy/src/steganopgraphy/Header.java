package steganopgraphy;

class Header 
{
  static final int HEADER_LENGTH = 25;
  String name;
  long size;
  
  Header()
  {
    name = "";
    size = 0;
  }
  
  Header(String name, long size)
  {
    this.name = name;
    this.size = size;
  }
  
  
  
  byte[] getHeader()
  {//"10 bytes size,14 bytes for name"
   //"45623     ,DB.zip        " 
    StringBuffer sbuff = new StringBuffer();
    sbuff.append(String.valueOf(size));
    while(sbuff.length() < 10)
      sbuff.append(' ');

    sbuff.append(',');
    
    //"computer".substring(3, 7) returns "pute"
    //"computer".substring(3) returns "puter"
    
    if(name.length() > 14)
      sbuff.append(name.substring(name.length()- 14)); 
    else
      sbuff.append(name); 
    
    while(sbuff.length() < HEADER_LENGTH)
      sbuff.append(' ');
    
    return sbuff.toString().getBytes();
  }
  
  long getSize(byte arr[])throws Exception
  {
    if(arr.length != HEADER_LENGTH)
      throw new Exception("Invalid Header Size");

    //first 10 bytes are of length
    //new String(arr, startIndex, length)
    return Long.parseLong(new String(arr, 0, 10).trim());
  }
  
  String getName(byte arr[])throws Exception
  {
    if(arr.length != HEADER_LENGTH)
      throw new Exception("Invalid Header Size");

    //first 10 bytes are of length
    //Next a comma
    //Then 14 bytes are of name
    return new String(arr, 11, arr.length-11).trim();
  }
}
