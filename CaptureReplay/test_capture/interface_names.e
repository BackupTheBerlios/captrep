indexing
	description	: "Strings for the Graphical User Interface"
	author		: "Generated by the New Vision2 Application Wizard."
	date		: "$Date: 2004/05/17 14:46:12 $"
	revision	: "1.0.0"

class
	INTERFACE_NAMES

feature -- Access

	Button_ok_item: STRING is "OK"
			-- String for "OK" buttons.

	Menu_file_item: STRING is "&File"
			-- String for menu "File"

	Menu_file_new_item: STRING is "&New%TCtrl+N"
			-- String for menu "File/New"

	Menu_file_open_item: STRING is "&Open...%TCtrl+O"
			-- String for menu "File/Open"

	Menu_file_save_item: STRING is "&Save%TCtrl+S"
			-- String for menu "File/Save"

	Menu_file_saveas_item: STRING is "Save &As..."
			-- String for menu "File/Save As"

	Menu_file_close_item: STRING is "&Close"
			-- String for menu "File/Close"

	Menu_file_exit_item: STRING is "E&xit"
			-- String for menu "File/Exit"

	Menu_help_item: STRING is "&Help"
			-- String for menu "Help"

	Menu_help_contents_item: STRING is "&Contents and Index"
			-- String for menu "Help/Contents and Index"

	Menu_help_about_item: STRING is "&About..."
			-- String for menu "Help/About"

	Label_confirm_close_window: STRING is "You are about to close this window.%NClick OK to proceed."
			-- String for the confirmation dialog box that appears
			-- when the user try to close the first window.

end -- class INTERFACE_NAMES
