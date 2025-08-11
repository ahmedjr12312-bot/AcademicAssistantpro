public class ManageCoursesActivity extends AppCompatActivity {
    private AppDatabase db; 
    private ListView list;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);
        
        db = AppDatabase.get(this);
        list = findViewById(R.id.list_courses);

        // إضافة المقررات الدراسية للقائمة
        findViewById(R.id.fab_add_course).setOnClickListener(v -> addDialog());

        // تحديث القائمة بالمقررات الدراسية
        refresh();
    }

    private void refresh(){
        List<Course> courses = db.courseDao().all();
        ArrayAdapter<Course> a = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        list.setAdapter(a);
        list.setOnItemLongClickListener((p,v,pos,id) -> {
            db.courseDao().delete(courses.get(pos)); 
            Toast.makeText(this,"تم الحذف",Toast.LENGTH_SHORT).show(); 
            refresh(); 
            return true;
        });
    }

    private void addDialog(){
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null, false);
        EditText name = v.findViewById(R.id.input_course_name), code = v.findViewById(R.id.input_course_code);

        new AlertDialog.Builder(this)
            .setTitle("إضافة مقرر")
            .setView(v)
            .setPositiveButton("حفظ", (d, w) -> {
                String n = name.getText().toString().trim(); 
                String c = code.getText().toString().trim();
                if (!n.isEmpty()) {
                    db.courseDao().insert(new Course(n, c)); 
                    refresh(); 
                }
            })
            .setNegativeButton("إلغاء", null)
            .show();
    }
}
