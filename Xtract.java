public class Xtract {
    public static void main(String[] args) {
 
        InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
           
        Pattern pattern = Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");
        Matcher matcher = pattern.matcher(ip.toString());
        if (matcher.find())
            System.out.println(matcher.group());
        
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }
    }
    
}