package com.example.quiz;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.quiz.R.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {


    /*
        Declaring variables:

        question will hold the textview in which question will be displayed

        questionNumber will hold the textview in which current question number will be displayed

        nextButton is a button that will take us to the next question

        questionList is a list of Model type. It basically holds question, all options and the correct answer
        (Please checkout Model class for more details)

        marks variable keeps the track of the total marks scored so far by the user

        optionContainer is a linear layout that holds the question textview and all option buttons vertically
        (Please checkout "fragment_question" fragment for more details)
    */

    private TextView question, questionNumber;
    private LinearLayout optionsContainer;
    private Button nextButton;
    private int counter = 1;
    List<Model> questionList;
    private int currentPosition = 0;
    private int marks = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
            Inflate the layout for this fragment
        */
        View view = inflater.inflate(layout.fragment_question, container, false);


        /*
            Initializing variables
        */
        question = view.findViewById(id.question);
        questionNumber = view.findViewById(id.questionNumber);
        nextButton = view.findViewById(id.nextButton);
        optionsContainer = view.findViewById(id.optionsLayout);

        /*

                Instantiate an ArrayList object

         */
        questionList = new ArrayList<>();

        /*
            Inserting questions, all option and correct option to the questionList
            by calling the constructor

         */
        questionList.add(new Model("What is the capital of India?", "Mumbai", "New Delhi", "Jaipur", "Banaras", "New Delhi"));
        questionList.add(new Model("Who is father of computer?", "Charles Babbage", "Dennis Ritche", "James Gosling", "David Lee", "Charles Babbage"));
        questionList.add(new Model("What is the capital of Uttarakhand?", "Nainital", "Dehradun", "Haldwani", "Ranibagh", "Dehradun"));
        questionList.add(new Model("When does the ArrayIndexOutOfBoundsException occur?", "Compile-time", "Run-time", "Not an error", "Not an exception at all", "Run-time"));
        questionList.add(new Model("In Operating Systems, which of the following is/are CPU scheduling algorithms?", "Priority", "Round Robin", "Shortest Job First", "All of the mentioned", "All of the mentioned"));

        /*
            Iterating over all the options buttons one by one. onClickListener is also attached so that
            if a user clicks on it we can take action
            Note that we are not starting from i = 0, this is because the first child of options container
            is the question textview (check fragment_question for more details)

         */


        for(int i = 1 ; i < 5 ; i++)
        {
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {

                    /*
                        Calling evaluateAnswer function,
                        since we want to change the color of the clicked button or option (if right or wrong). Therefore,
                        we have casted the view into button and passed to the evaluateAnswer function

                     */
                    evaluateAnswer((Button) view);
                }
            });
        }


        /*

            When the first time user opens the fragment_fragment
            Play the animation
         */


        /*
                For the first question, "NEXT" button has to be disabled by default
                and also make it appear less opaque
        */
            nextButton.setEnabled(false);
            nextButton.setAlpha((float) 0.6);

        /*
            Each time next button is clicked
         */

        playAnimation(question, 0, questionList.get(currentPosition).getQuestion());
        nextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                /*
                    Disable the nextButton
                    Make it less opaque
                    call toggleState function by passing true since
                    we want to enable all the option buttons for the next question

                 */
                nextButton.setEnabled(false);
                nextButton.setAlpha((float) 0.6);

                toggleOptionState(true);

                /*

                    Also increment the value of currentPosition
                    It simply means that we want to deal with the next item in the
                    questionList
                 */
                currentPosition++;

                /*
                    If the current position becomes equal to the
                    questionList size then it means we are done with all the questions
                 */

                if(currentPosition == questionList.size())
                {
                    /*
                        Play the celebration sound
                    */
                    MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), raw.celebration);
                    mediaPlayer.start();

                    /*
                        Here, we transferring marks and total marks to the score fragment
                    */
                    Bundle args = new Bundle();
                    args.putString("Marks", String.valueOf(marks));
                    args.putString("Total Marks", String.valueOf(questionList.size()));
                    Navigation.findNavController(view).navigate(id.action_questionFragment_to_scoreFragment, args);
                    return;
                }

                /*
                    Set the counter to one
                 */

                counter = 1;

                /*
                    Play the animation
                 */

                playAnimation(question, 0, questionList.get(currentPosition).getQuestion());
            }
        });
        return view;
    }

    /*
        Function to animate views
     */

    private void playAnimation(final View view, final int visible, final String data)
    {
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(visible).scaleX(visible).scaleY(visible).setDuration(500).setStartDelay(60)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                if(visible == 0 && counter < 5)
                {
                    String option = "";

                    switch (counter)
                    {
                        // Option a
                        case 1:
                            option = questionList.get(currentPosition).getaOption();
                            break;
                        case 2:
                            option = questionList.get(currentPosition).getbOption();
                            break;
                        case 3:
                            option = questionList.get(currentPosition).getcOption();
                            break;
                        case 4:
                            option = questionList.get(currentPosition).getdOption();
                            break;
                    }

                    playAnimation(optionsContainer.getChildAt(counter), 0, option);
                    counter++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if(visible == 0)
                {
                    try{
                        questionNumber.setText("Question " + (currentPosition + 1));
                        /*
                            Set the text on
                         */

                        ((TextView) view).setText(data);

                    }
                    catch (ClassCastException exception)
                    {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnimation(view, 1, data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void evaluateAnswer(Button choosenOption)
    {
        /*
            Once the user has selected an option then we are required to
            disable all the options buttons immediately
        */

        toggleOptionState(false);

        /*
            Since, the user has selected an option. Hence, make the "NEXT" button enabled
            and perfectly visible
        */


        nextButton.setEnabled(true);
        nextButton.setAlpha(1);

        // The option selected by the user is correct
        if(choosenOption.getText().toString().equals(questionList.get(currentPosition).getCorrectAnswer()))
        {

            MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), raw.correct_answer);
            mediaPlayer.start();
            /*
                Then increment the marks scored by the user so far
            */
            marks++;

            /*
                Also change the color of the selected option to green
            */
            choosenOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0E9815")));
        }

        // Otherwise
        else
        {

            MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), raw.wrong_answer);
            mediaPlayer.start();

            /*
                Change the selected option to red
            */
            choosenOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E53935")));

            /*
                Find the button with the help of tag
            */
            Button correctAnswer = (Button) optionsContainer.findViewWithTag(questionList.get(currentPosition).getCorrectAnswer());

            /*
                Also change the color of the correct option to green
            */
            correctAnswer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0E9815")));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void toggleOptionState(boolean status)
    {

        for(int i = 1 ; i < 5 ; i++)
        {
            /*
                Enable option buttons if status is true
                Otherwise, disable them

             */
            optionsContainer.getChildAt(i).setEnabled(status);

            /*
                    If status is true
            */
            if(status)
            {
                /*
                    Change the drawable of option buttons

                */
                optionsContainer.getChildAt(i).setBackgroundTintList(null);
                optionsContainer.getChildAt(i).setBackgroundResource(drawable.options_button_border);
            }
        }
    }
}