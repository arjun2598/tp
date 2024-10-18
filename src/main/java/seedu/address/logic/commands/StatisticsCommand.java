package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;

/**
 * Displays the overall statistics of the people in the addressbook.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the overall statistics regarding all the people in SocialBook.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_DISPLAY_STATISTICS_SUCCESS = "Here are all the statistics:\n%s";
    public static final String MESSAGE_DISPLAY_TOTAL_PEOPLE = "Total Number Of People: %s";
    public static final String MESSAGE_DISPLAY_HIGH_PRIORITY = "Number Of HIGH Priority People: %s";
    public static final String MESSAGE_DISPLAY_MEDIUM_PRIORITY = "Number Of MEDIUM Priority People: %s";
    public static final String MESSAGE_DISPLAY_LOW_PRIORITY = "Number Of LOW Priority People: %s";
    private String resultMessage = "";

    /**
     * Displays all the overall statistics to be shown.
     *
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        String[] allStats = new String[4];
        allStats[0] = nbOfPeople(lastShownList);
        allStats[1] = highPriorityPeople(lastShownList);
        allStats[2] = mediumPriorityPeople(lastShownList);
        allStats[3] = lowPriorityPeople(lastShownList);

        resultMessage = Arrays.stream(allStats)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_DISPLAY_STATISTICS_SUCCESS, resultMessage));
    }

    /**
     * Returns a message with total number of people in current list.
     *
     * @param currList current list.
     * @return string message of total number of people.
     */
    public static String nbOfPeople(List<Person> currList) {
        return String.format(MESSAGE_DISPLAY_TOTAL_PEOPLE, currList.size());
    }

    /**
     * Returns a message with number of high priority people in current list.
     *
     * @param currList current list.
     * @return string message of number of high priority people.
     */
    public static String highPriorityPeople(List<Person> currList) {
        long highPriority = currList.stream()
                .filter(person -> person.getPriority() == Priority.HIGH)
                .count();
        return String.format(MESSAGE_DISPLAY_HIGH_PRIORITY, highPriority);
    }

    /**
     * Returns a message with number of medium priority people in current list.
     *
     * @param currList current list.
     * @return string message of number of medium priority people.
     */
    public static String mediumPriorityPeople(List<Person> currList) {
        long mediumPriority = currList.stream()
                .filter(person -> person.getPriority() == Priority.MEDIUM)
                .count();
        return String.format(MESSAGE_DISPLAY_MEDIUM_PRIORITY, mediumPriority);
    }

    /**
     * Returns a message with number of low priority people in current list.
     *
     * @param currList current list.
     * @return string message of number of low priority people.
     */
    public static String lowPriorityPeople(List<Person> currList) {
        long lowPriority = currList.stream()
                .filter(person -> person.getPriority() == Priority.LOW)
                .count();
        return String.format(MESSAGE_DISPLAY_LOW_PRIORITY, lowPriority);
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatisticsCommand)) {
            return false;
        }

        StatisticsCommand otherStatisticsCommand = (StatisticsCommand) other;
        return resultMessage.equals(otherStatisticsCommand.getResultMessage());
    }

}
