
public class AstroidBreaker {
    public static int breakAstroid(int size)
    {
        switch(size){
            case 5:
            case 3:
            case 2:
                return 2;
            case 1:
                return 0;
            default: 
                return 0;
        }
    }    
    public static int getNewSize(int currentSize){
        int newSize = 0;
        switch (currentSize) {
            case 5:
                newSize = 3;
                break;
            case 3:
            case 2:
                newSize = 1;
            default:
                newSize = 0;
                break;
        }
        return newSize;
    }
}
