package mediator.colleague;

public class Night extends WildeLand {
    public Night(String date) {
        super(date);
    }

    /**
     * This method is an implementation of the Mediator method.
     *
     * @return String "Night, day:time:counter"
     */
    @Override
    public String whatTimeIsIt() {
        return "Night " + super.date;
    }

    @Override
    public String howIsTheWeather(String date) {
        return "Tonight's forecast: \n   [" + whatTimeIsIt() + "] " + super.getWeather() + "\n";
    }
}