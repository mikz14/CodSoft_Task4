package codsoft_task4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    String question;
    List<String> options;
    int correctOption;

    QuizQuestion(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
}

public class Codsoft_Task4 {
    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex;
    private int userScore;
    private Timer timer;
    private Scanner scanner;

    public Codsoft_Task4() {
        quizQuestions = new ArrayList<>();
        initializeQuestions(); // Add your questions here
        currentQuestionIndex = 0;
        userScore = 0;
        timer = new Timer();
        scanner = new Scanner(System.in);
    }

    private void initializeQuestions() {
        quizQuestions.add(new QuizQuestion("What is the capital of France?",
                Arrays.asList("1. Berlin", "2. Madrid", "3. Paris", "4. Rome"), 2));

        quizQuestions.add(new QuizQuestion("\nWhich planet is known as the Red Planet?",
                Arrays.asList("1. Venus", "2. Mars", "3. Jupiter", "4. Saturn"), 1));
        
        
        quizQuestions.add(new QuizQuestion("\nWhat is the capital of Spain?",
                Arrays.asList("1. Berlin", "2. Madrid", "3. Barcelona", "4. Rome"), 1));
        
        quizQuestions.add(new QuizQuestion("\nWhat is the capital of China?",
                Arrays.asList("1. Berlin", "2. Madrid", "3. Beijing", "4. Rome"), 2));
        
        quizQuestions.add(new QuizQuestion("\nWhat is the capital of Germany?",
                Arrays.asList("1. Berlin", "2. Madrid", "3. Paris", "4. Rome"), 0));
    }

    private void displayQuestion() {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);
        System.out.println(currentQuestion.question);
        for (String option : currentQuestion.options) {
            System.out.println(option);
        }
        startTimer();
    }

    private void displayUserPrompt() {
        System.out.println("\nYour choice (1-4): ");
    }

    private void startTimer() {
        int timeLimitInSeconds = 10;

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int secondsRemaining = timeLimitInSeconds;

            @Override
            public void run() {
                System.out.print("Time Remaining:"+secondsRemaining+ ", ");
                if (secondsRemaining == 0) {
                    timer.cancel(); // Stop the timer
                    System.out.println("\nTime's up! Moving to the next question.");
                    evaluateAnswer(-1); // Evaluate answer when time is up
                }
                secondsRemaining--;
            }
        }, 0, 1000);
    }

    private void evaluateAnswer(int userChoice) {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        if (userChoice == currentQuestion.correctOption) {
            System.out.println("Correct!");
            userScore++;
        } else if (userChoice == -1) {
            System.out.println("The correct answer was option " + (currentQuestion.correctOption + 1));
        } else {
            System.out.println("Incorrect! The correct answer was option " + (currentQuestion.correctOption + 1));
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < quizQuestions.size()) {
            displayQuestion();
        } else {
            endGame();
        }
    }

    private void endGame() {
        System.out.println("\nQuiz completed!");
        System.out.println("Your final score: " + userScore + "/" + quizQuestions.size());
        scanner.close();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the quiz game. Just select an option between 1 - 4 and click enter to answer to answer the questions.");
        Codsoft_Task4 quizGame = new Codsoft_Task4();
        quizGame.displayQuestion();

        while (quizGame.currentQuestionIndex < quizGame.quizQuestions.size()) {
            quizGame.displayUserPrompt();
            int userChoice = quizGame.scanner.nextInt();

            if (userChoice >= 1 && userChoice <= 4) {
                quizGame.timer.cancel(); // Stop the timer when user inputs the choice
                quizGame.evaluateAnswer(userChoice - 1);
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}


