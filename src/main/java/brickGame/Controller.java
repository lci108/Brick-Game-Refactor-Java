package brickGame;

public class Controller {
    private Main main; // this is going to be model
    private View view;

    public Controller(Main main, View view) {
        this.main = main;
        this.view = view;
    }

    public void someAction() {
//        view.setHeart(main.getHeart());

    }
}
