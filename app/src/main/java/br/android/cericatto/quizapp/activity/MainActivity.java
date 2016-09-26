package br.android.cericatto.quizapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.android.cericatto.quizapp.R;
import br.android.cericatto.quizapp.data.DataItems;

/**
 * MainActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Sep 25, 2016
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    private static final Integer RIGHT_QUESTIONS_NUMBER = 7;
    private static final Integer ANSWERS_NUMBER = 5;
    private static final Integer QUESTIONS_ASKED = 6;

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * Contexts.
     */

    private Activity mActivity = MainActivity.this;

    /**
     * Layout.
     */

    private LinearLayout mQuestionsLinearLayout;
    private LinearLayout mResultLinearLayout;

    private LinearLayout mRadioLinearLayout;
    private LinearLayout mCheckBoxLinearLayout;

    private TextView mResultTextView;
    private TextView mQuestionTextView;
    private Button mNextQuestionButton;

    /**
     * Answers.
     */

    private Integer mIndex = 1;
    private Integer[] mRightAnswersId = new Integer[] { 3, 1, 2, 1, 3, 1, 2 };
    private Integer[] mAnswersId = new Integer[] { 0, 0, 0, 0, 0, 0, 1 };

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(false);
        setLayout();
    }

    //--------------------------------------------------
    // View Methods
    //--------------------------------------------------

    private void initToolbar(Boolean homeEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
            String text = getString(R.string.activity_main__question_number) + mIndex;
            getSupportActionBar().setTitle(text);
        }
    }

    private void setLayout() {
        int questionIndex = mIndex - 1;
        int answerIndex = questionIndex * ANSWERS_NUMBER;
        setLinearLayouts();
        setViews(questionIndex);

        if (mIndex < QUESTIONS_ASKED) {
            setRadioButtons(answerIndex);
        } else if (mIndex == QUESTIONS_ASKED) {
            setCheckBoxes(answerIndex);
        }
    }

    private void setLinearLayouts() {
        mQuestionsLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__questions_linear_layout);
        mResultLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__result_linear_layout);

        mRadioLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__radio_answers_linear_layout);
        mCheckBoxLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__check_box_answers_linear_layout);
    }

    private void setViews(Integer questionIndex) {
        mResultTextView = (TextView)findViewById(R.id.id_activity_main__result_text_view);

        if (mIndex <= QUESTIONS_ASKED) {
            mQuestionTextView = (TextView) findViewById(R.id.id_activity_main__question_text_view);
            mQuestionTextView.setText(DataItems.QUESTIONS[questionIndex]);

            mNextQuestionButton = (Button) findViewById(R.id.id_activity_main__next_question_button);
            mNextQuestionButton.setOnClickListener(this);
        }
    }

    private void setRadioButtons(int answerIndex) {
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.id_activity_main__radio_group);
        radioGroup.setOnCheckedChangeListener(this);

        RadioButton firstRadioButton = (RadioButton)findViewById(R.id.id_activity_main__first_radio_button);
        RadioButton secondRadioButton = (RadioButton)findViewById(R.id.id_activity_main__second_radio_button);
        RadioButton thirdRadioButton = (RadioButton)findViewById(R.id.id_activity_main__third_radio_button);
        RadioButton fourthRadioButton = (RadioButton)findViewById(R.id.id_activity_main__fourth_radio_button);
        RadioButton fifthRadioButton = (RadioButton)findViewById(R.id.id_activity_main__fifth_radio_button);

        answerIndex = setRadioButton(firstRadioButton, answerIndex, true);
        answerIndex = setRadioButton(secondRadioButton, answerIndex, false);
        answerIndex = setRadioButton(thirdRadioButton, answerIndex, false);
        answerIndex = setRadioButton(fourthRadioButton, answerIndex, false);
        setRadioButton(fifthRadioButton, answerIndex, false);
    }

    private int setRadioButton(RadioButton radioButton, int answerIndex, boolean check) {
        radioButton.setText(DataItems.ANSWERS[answerIndex]);
        radioButton.setChecked(check);
        return (answerIndex + 1);
    }

    private void setCheckBoxes(int answerIndex) {
        CheckBox firstCheckBox = (CheckBox)findViewById(R.id.id_activity_main__first_check_box);
        CheckBox secondCheckBox = (CheckBox)findViewById(R.id.id_activity_main__second_check_box);
        CheckBox thirdCheckBox = (CheckBox)findViewById(R.id.id_activity_main__third_check_box);
        CheckBox fourthCheckBox = (CheckBox)findViewById(R.id.id_activity_main__fourth_check_box);
        CheckBox fifthCheckBox = (CheckBox)findViewById(R.id.id_activity_main__fifth_check_box);

        answerIndex = setCheckBox(firstCheckBox, answerIndex, true);
        answerIndex = setCheckBox(secondCheckBox, answerIndex, true);
        answerIndex = setCheckBox(thirdCheckBox, answerIndex, false);
        answerIndex = setCheckBox(fourthCheckBox, answerIndex, false);
        setCheckBox(fifthCheckBox, answerIndex, false);
    }

    private Integer setCheckBox(CheckBox checkBox, int answerIndex, boolean check) {
        checkBox.setText(DataItems.ANSWERS[answerIndex]);
        checkBox.setChecked(check);
        checkBox.setOnClickListener(this);
        return (answerIndex + 1);
    }

    //--------------------------------------------------
    // Other Methods
    //--------------------------------------------------

    private void setNextQuestion() {
        mIndex++;
        if (mIndex >= (QUESTIONS_ASKED + 1)) {
            setGradingLayout();
        } else {
            setQuestionLayout();
        }
    }

    private void setGradingLayout() {
        mQuestionsLinearLayout.setVisibility(View.GONE);
        mResultLinearLayout.setVisibility(View.VISIBLE);

        getSupportActionBar().setTitle(R.string.activity_main__result);
        Integer guesses = getGuessNumber();
        String text = getString(R.string.activity_main__guessed, guesses, QUESTIONS_ASKED);
        Toast.makeText(mActivity, text, Toast.LENGTH_LONG).show();
        mResultTextView.setText(text);
    }

    private void setQuestionLayout() {
        if (mIndex == QUESTIONS_ASKED) {
            mQuestionsLinearLayout.setVisibility(View.VISIBLE);
            mResultLinearLayout.setVisibility(View.GONE);

            mRadioLinearLayout.setVisibility(View.GONE);
            mCheckBoxLinearLayout.setVisibility(View.VISIBLE);

            mNextQuestionButton.setText(R.string.activity_main__show_grading);
        }
        setLayout();
        initToolbar(false);
    }

    private Integer getGuessNumber() {
        // Checks radio buttons answers.
        Integer cont = 0;
        for (int i = 0; i < mAnswersId.length - 1; i++) {
            if (mAnswersId[i] == mRightAnswersId[i]) {
                cont++;
            }
        }

        // Checks check box answers.
        Boolean firstCorrectAnswer =
            (mAnswersId[RIGHT_QUESTIONS_NUMBER - 2] == mRightAnswersId[RIGHT_QUESTIONS_NUMBER - 2])
            || (mAnswersId[RIGHT_QUESTIONS_NUMBER - 2] == mRightAnswersId[RIGHT_QUESTIONS_NUMBER - 1]);
        Boolean secondCorrectAnswer =
            (mAnswersId[RIGHT_QUESTIONS_NUMBER - 1] == mRightAnswersId[RIGHT_QUESTIONS_NUMBER - 2])
            || (mAnswersId[RIGHT_QUESTIONS_NUMBER - 1] == mRightAnswersId[RIGHT_QUESTIONS_NUMBER - 1]);
        if (firstCorrectAnswer && secondCorrectAnswer) {
            cont++;
        }
        return cont;
    }

    private void setCheckBoxAnswerId(boolean isChecked, int index) {
        if (isChecked) {
            mAnswersId[mIndex - 1] = index;
        }
    }

    //--------------------------------------------------
    // RadioGroup.OnCheckedChangeListener
    //--------------------------------------------------

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.id_activity_main__first_radio_button:
                mAnswersId[mIndex - 1] = 0;
                break;
            case R.id.id_activity_main__second_radio_button:
                mAnswersId[mIndex - 1] = 1;
                break;
            case R.id.id_activity_main__third_radio_button:
                mAnswersId[mIndex - 1] = 2;
                break;
            case R.id.id_activity_main__fourth_radio_button:
                mAnswersId[mIndex - 1] = 3;
                break;
            case R.id.id_activity_main__fifth_radio_button:
                mAnswersId[mIndex - 1] = 4;
                break;
        }
    }

    //--------------------------------------------------
    // View.OnClickListener
    //--------------------------------------------------

    @Override
    public void onClick(View view) {
        Integer id = view.getId();
        if (id == R.id.id_activity_main__next_question_button) {
            setNextQuestion();
        } else {
            if (mIndex <= RIGHT_QUESTIONS_NUMBER) {
                CheckBox checkBox = (CheckBox)view;
                boolean isChecked = checkBox.isChecked();
                switch (id) {
                    case R.id.id_activity_main__first_check_box:
                        setCheckBoxAnswerId(isChecked, 0);
                        break;
                    case R.id.id_activity_main__second_check_box:
                        setCheckBoxAnswerId(isChecked, 1);
                        break;
                    case R.id.id_activity_main__third_check_box:
                        setCheckBoxAnswerId(isChecked, 2);
                        break;
                    case R.id.id_activity_main__fourth_check_box:
                        setCheckBoxAnswerId(isChecked, 3);
                        break;
                    case R.id.id_activity_main__fifth_check_box:
                        setCheckBoxAnswerId(isChecked, 4);
                        break;
                }
                mIndex++;
            }
        }
    }
}