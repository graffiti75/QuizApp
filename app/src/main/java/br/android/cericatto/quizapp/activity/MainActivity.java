package br.android.cericatto.quizapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private static final Integer QUESTIONS_ASKED = 7;

    private static final String CASSIUS_CLAY = "cassius clay";

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
    private LinearLayout mTextEntryLinearLayout;

    private TextView mResultTextView;
    private EditText mEditText;
    private TextView mQuestionTextView;
    private Button mNextQuestionButton;

    private CheckBox mFirstCheckBox;
    private CheckBox mSecondCheckBox;
    private CheckBox mThirdCheckBox;
    private CheckBox mFourthCheckBox;
    private CheckBox mFifthCheckBox;

    /**
     * Answers.
     */

    private Integer mCurrentQuestion = 1;
    private Integer[] mRightAnswersId = new Integer[] { 3, 1, 2, 1, 3, 1, 2 };
    private Integer[] mAnswersId = new Integer[] { 0, 0, 0, 0, 0, 0, 1 };
    private String mTextEntryAnswer = "";

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
            String text = getString(R.string.activity_main__question_number) + mCurrentQuestion;
            getSupportActionBar().setTitle(text);
        }
    }

    private void setLayout() {
        int questionIndex = mCurrentQuestion - 1;
        int answerIndex = questionIndex * ANSWERS_NUMBER;
        setLinearLayouts();
        setViews(questionIndex);

        if (mCurrentQuestion == (QUESTIONS_ASKED - 1)) {
            setCheckBoxes(answerIndex);
        } else if (mCurrentQuestion < (QUESTIONS_ASKED - 1)) {
            setRadioButtons(answerIndex);
        }
    }

    private void setLinearLayouts() {
        mQuestionsLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__questions_linear_layout);
        mResultLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__result_linear_layout);

        mRadioLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__radio_answers_linear_layout);
        mCheckBoxLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__check_box_answers_linear_layout);
        mTextEntryLinearLayout = (LinearLayout)findViewById(R.id.id_activity_main__text_entry_answers_linear_layout);
    }

    private void setViews(Integer questionIndex) {
        mResultTextView = (TextView)findViewById(R.id.id_activity_main__result_text_view);

        if (mCurrentQuestion < QUESTIONS_ASKED) {
            mQuestionTextView = (TextView) findViewById(R.id.id_activity_main__question_text_view);
            mQuestionTextView.setText(DataItems.QUESTIONS[questionIndex]);

            mNextQuestionButton = (Button) findViewById(R.id.id_activity_main__next_question_button);
            mNextQuestionButton.setOnClickListener(this);
        }
        if (mCurrentQuestion == QUESTIONS_ASKED) {
            mEditText = (EditText) findViewById(R.id.id_activity_main__edit_text);
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
        mFirstCheckBox = (CheckBox)findViewById(R.id.id_activity_main__first_check_box);
        mSecondCheckBox = (CheckBox)findViewById(R.id.id_activity_main__second_check_box);
        mThirdCheckBox = (CheckBox)findViewById(R.id.id_activity_main__third_check_box);
        mFourthCheckBox = (CheckBox)findViewById(R.id.id_activity_main__fourth_check_box);
        mFifthCheckBox = (CheckBox)findViewById(R.id.id_activity_main__fifth_check_box);

        answerIndex = setCheckBox(mFirstCheckBox, answerIndex, true);
        answerIndex = setCheckBox(mSecondCheckBox, answerIndex, true);
        answerIndex = setCheckBox(mThirdCheckBox, answerIndex, false);
        answerIndex = setCheckBox(mFourthCheckBox, answerIndex, false);
        setCheckBox(mFifthCheckBox, answerIndex, false);
    }

    private Integer setCheckBox(CheckBox checkBox, int answerIndex, boolean check) {
        checkBox.setText(DataItems.ANSWERS[answerIndex]);
        checkBox.setChecked(check);
        return (answerIndex + 1);
    }

    //--------------------------------------------------
    // Other Methods
    //--------------------------------------------------

    private void setNextQuestion() {
        mCurrentQuestion++;
        if (mCurrentQuestion > QUESTIONS_ASKED) {
            setGradingLayout();
        } else if (mCurrentQuestion == QUESTIONS_ASKED) {
            setTextEntryQuestionLayout();
        } else {
            setQuestionLayout();
        }
    }

    private void setGradingLayout() {
        mTextEntryAnswer = mEditText.getText().toString().toLowerCase();

        mQuestionsLinearLayout.setVisibility(View.GONE);
        mResultLinearLayout.setVisibility(View.VISIBLE);

        getSupportActionBar().setTitle(R.string.activity_main__result);
        Integer guesses = getGuessNumber();
        String text = getString(R.string.activity_main__guessed, guesses, QUESTIONS_ASKED);
        Toast.makeText(mActivity, text, Toast.LENGTH_LONG).show();
        mResultTextView.setText(text);
    }

    private void setQuestionLayout() {
        if (mCurrentQuestion == (QUESTIONS_ASKED - 1))  {
            mQuestionsLinearLayout.setVisibility(View.VISIBLE);
            mResultLinearLayout.setVisibility(View.GONE);

            mRadioLinearLayout.setVisibility(View.GONE);
            mCheckBoxLinearLayout.setVisibility(View.VISIBLE);
            mTextEntryLinearLayout.setVisibility(View.GONE);
        }
        setLayout();
        initToolbar(false);
    }

    private void setTextEntryQuestionLayout() {
        if (mCurrentQuestion == QUESTIONS_ASKED) {
            mQuestionsLinearLayout.setVisibility(View.VISIBLE);
            mResultLinearLayout.setVisibility(View.GONE);

            mRadioLinearLayout.setVisibility(View.GONE);
            mCheckBoxLinearLayout.setVisibility(View.GONE);
            mTextEntryLinearLayout.setVisibility(View.VISIBLE);

            mNextQuestionButton.setText(R.string.activity_main__show_grading);
        }
        setLayout();
        initToolbar(false);

        mQuestionTextView.setText(R.string.activity_main__text_entry_question);
    }

    private Integer getGuessNumber() {
        // Checks RadioButton's answers.
        Integer cont = 0;
        for (int i = 0; i < mAnswersId.length - 1; i++) {
            if (mAnswersId[i] == mRightAnswersId[i]) {
                cont++;
            }
        }

        // Checks CheckBox'es answers.
        Boolean checkBoxesChecked = mSecondCheckBox.isChecked() && mThirdCheckBox.isChecked();
        Boolean checkBoxesNotChecked = !mFirstCheckBox.isChecked() && !mFourthCheckBox.isChecked()
            && !mFifthCheckBox.isChecked();
        if (checkBoxesChecked && checkBoxesNotChecked) {
            cont++;
        }

        // Check TextEntry answer.
        if (mTextEntryAnswer.equals(CASSIUS_CLAY)) {
            cont++;
        }

        return cont;
    }

    //--------------------------------------------------
    // RadioGroup.OnCheckedChangeListener
    //--------------------------------------------------

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.id_activity_main__first_radio_button:
                mAnswersId[mCurrentQuestion - 1] = 0;
                break;
            case R.id.id_activity_main__second_radio_button:
                mAnswersId[mCurrentQuestion - 1] = 1;
                break;
            case R.id.id_activity_main__third_radio_button:
                mAnswersId[mCurrentQuestion - 1] = 2;
                break;
            case R.id.id_activity_main__fourth_radio_button:
                mAnswersId[mCurrentQuestion - 1] = 3;
                break;
            case R.id.id_activity_main__fifth_radio_button:
                mAnswersId[mCurrentQuestion - 1] = 4;
                break;
        }
    }

    //--------------------------------------------------
    // View.OnClickListener
    //--------------------------------------------------

    @Override
    public void onClick(View view) {
        setNextQuestion();
    }
}