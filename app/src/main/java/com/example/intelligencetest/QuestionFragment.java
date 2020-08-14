package com.example.intelligencetest;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intelligencetest.databinding.FragmentQuestionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel myViewModel;
        myViewModel =new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),this)).get(MyViewModel.class);
        myViewModel.generator();
        myViewModel.get_currentscore().setValue(0);                             //让重新开始时 当前得分清空
        final FragmentQuestionBinding binding;
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_question,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        final StringBuilder builder=new StringBuilder();                          //可变字符序列
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.button1:
                        builder.append("1");
                        break;
                    case R.id.button2:
                        builder.append("2");
                        break;
                    case R.id.button3:
                        builder.append("3");
                        break;
                    case R.id.button4:
                        builder.append("4");
                        break;
                    case R.id.button5:
                        builder.append("5");
                        break;
                    case R.id.button6:
                        builder.append("6");
                        break;
                    case R.id.button7:
                        builder.append("7");
                        break;
                    case R.id.button8:
                        builder.append("8");
                        break;
                    case R.id.button9:
                        builder.append("9");
                        break;
                    case R.id.button10:
                        builder.setLength(0);
                        break;
                    case R.id.button11:
                        builder.append("0");
                        break;
                }
                if(builder.length()==0){
                    binding.textView8.setText(getString(R.string.index));
                }else {
                    binding.textView8.setText(builder.toString());
                }
            }
        };
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.button10.setOnClickListener(listener);
        binding.button11.setOnClickListener(listener);

        binding.button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(builder.length()==0){                            //当没有输入值时，builder的值是空，tostring会报错
                    builder.append("-1");
                }
                if(Integer.valueOf(builder.toString()).intValue()==myViewModel.get_answer().getValue()){
                    myViewModel.answerScoreCorrect();
                    builder.setLength(0);
                    binding.textView8.setText(R.string.answer_message);
                    myViewModel.saved();
                    //builder.append(getString(R.string.answer_message));                  //这个会造成输入答案后提示和答案拼接在一起显示
                }
                else{
                    NavController controller=Navigation.findNavController(view);
                    if(myViewModel.win_flag){
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                        myViewModel.win_flag=false;
                        myViewModel.saved();
                    }else{
                        controller.navigate(R.id.action_questionFragment_to_fauilFragment);
                    }
                }
            }
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_question, container, false);
    }
}