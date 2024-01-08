from cx_Freeze import setup, Executable

build_exe_options = {
    'includes': ['mysql.connector','getpass'],  # Add any other necessary libraries here
}

setup(
    name="databaseSimplifier",
    version="1.0",
    description="Simplify DBMS Lab",
    options={'build_exe': build_exe_options},
    executables=[Executable("database.py")]
)
