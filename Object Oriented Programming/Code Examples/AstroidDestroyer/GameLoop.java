
public class GameLoop {
    private boolean running = true;
    private int frameCount = 0;
    
    private static final double TARGET_TIME = 1000.0/60.0;

    public void start()
    {
        while(running){
            long startTime = System.currentTimeMillis();
            //Remember to multiply deltaTime to the speed everytime in update of spaceship, astroid, etc,.
            update();
            render();

            frameCount++;
            if(frameCount % 60 == 0)
                System.out.println("Current Frame Count:\n" + frameCount);

            // Tasks to do in one frame ends here

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = (long)(TARGET_TIME - elapsedTime);

            if(sleepTime > 0)
            {
                try {
                    System.out.println("System Sleeping for " + sleepTime + "ms");
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // add a varibale that stores elapsed time of the previous frame

        }
    }

    public void update()
    {

    }
    public void render()
    {

    }

}
