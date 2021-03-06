indexing
	description: "Receives and dispatch the Windows messages to the Eiffel objects."
	status: "See notice at end of class."
	date: "$Date: 2004/05/27 16:11:02 $"
	revision: "$Revision: 1.5 $"

class
	WEL_DISPATCHER

inherit
	--CAPTURE_REPLAY_SHARED_INFO -- CAPTURE-ADDON
	CAPTURE_REPLAY_HANDLING
	
	WEL_WINDOW_MANAGER
		export
			{NONE} all
		end

	MEMORY
		export
			{NONE} all
		redefine
			dispose
		end

	WEL_WM_CONSTANTS
		export
			{NONE} all
		end

	WEL_EN_CONSTANTS	
		-- debug

create
	make

feature {NONE} -- Initialization

	make is
			-- Initialize the C variables
		do
			cwel_set_window_procedure_address ($window_procedure)
			cwel_set_dialog_procedure_address ($dialog_procedure)
			cwel_set_dispatcher_object (Current)
		end

feature {NONE} -- Implementation

	frozen window_procedure (hwnd: POINTER; msg, wparam,
			lparam: INTEGER): INTEGER is
			-- Window messages dispatcher routine
		local
			window: WEL_WINDOW
			returned_value: INTEGER
			has_return_value: BOOLEAN
		do
--io.putstring ("%NEntrer dans window procédure%N")
			window := window_of_item (hwnd)
			if window /= Void then
				if window.exists then
					debug ("win_dispatcher")
						print ("After look at windows table ")
						print (window.generating_type)
						io.new_line
					end
					window.increment_level
	
					Result := window.process_message (hwnd, msg, wparam, lparam)
						--| Store `message_return_value' and `has_return_value' for later
						--| use, since `call_default_window_procedure' can reset their value.
					if
						window.has_return_value
					then
						returned_value := window.message_return_value
						has_return_value := window.has_return_value
					end
	
					if window.default_processing then
						Result := window.call_default_window_procedure (hwnd, msg, wparam, lparam)
					end
	
					if has_return_value then
						Result := returned_value
					end
	
					window.decrement_level
				else
					Result := window.call_default_window_procedure (hwnd, msg, wparam, lparam)
				end
			else
				Result := cwin_def_window_proc (hwnd, msg, wparam, lparam)
			end
		end

	frozen dialog_procedure (hwnd: POINTER; msg, wparam,
			lparam: INTEGER): INTEGER is
			-- Dialog box messages dispatcher routine
		local
			window: WEL_WINDOW
			last_result: INTEGER
		do
if is_candidate_msg (msg) then
io.putstring ("%NEntrer dans dialogue procédure")
io.putstring ("message pouvant etre capturé : ")
io.putint (msg)
--else 
--io.putstring ("%NEntrer dans dialogue procédure")
--	io.putstring ("%NMessage = ")
--	io.putint (msg)
--	io.putstring ("%N")
end
			debug ("dlg_dispatcher")
				io.put_string ("in dlg_proc ")
				io.put_string (hwnd.out)
				io.put_character (' ')
				io.put_integer (msg)
				io.put_character (' ')
				io.put_boolean (msg = Wm_initdialog)
				io.put_new_line
			end
			if msg = Wm_initdialog then
				window := new_dialog
				if window /= Void then
					new_dialog_cell.put (Void)
					window.increment_level
	
					-- Special case for the message
					-- Wm_initdialog. We set the hwnd value
					-- to the object because this value
					-- was unknown until now.
					--| Since it is not possible to check
					--| if `set_focus' from WEL_WINDOW has been
					--| called, Result is set to `1'.
					--| As a result, any call to `set_focus' in
					--| `setup_dialog' from WEL_DIALOG is useless.
					window.set_item (hwnd)
					register_window (window)
					Result := window.process_message (hwnd, msg, wparam, lparam)
					window.decrement_level
				end
				Result := 1
			else
				window := window_of_item (hwnd)
				if window /= Void and window.exists then
					window.increment_level
					last_result := window.process_message (hwnd, msg, wparam, lparam)
					if window.has_return_value then
						Result := window.message_return_value
					else
						if not window.default_processing then
							Result := 1
						else
							Result := 0
						end
					end
					window.decrement_level
				end
			end
		end

feature {NONE} -- Implementation

	dispose is
			-- Wean `Current'
		local
			default_object: like Current
		do
			cwel_release_dispatcher_object
			cwel_set_dispatcher_object (default_object)
		end

feature {NONE} -- Externals

	cwel_integer_to_pointer (i: INTEGER): POINTER is
			-- Converts an integer `i' to a pointer
		external
			"C [macro %"wel.h%"] (EIF_INTEGER): EIF_POINTER"
		end

	cwel_temp_dialog_value: POINTER is
		external
			"C [macro %"wel.h%"]: EIF_POINTER"
		end

	cwel_set_window_procedure_address (address: POINTER) is
		external
			"C [macro %"disptchr.h%"]"
		end

	cwel_set_dialog_procedure_address (address: POINTER) is
		external
			"C [macro %"disptchr.h%"]"
		end

	cwel_set_dispatcher_object (dispatcher: like Current) is
		external
			"C macro signature (EIF_OBJECT) use %"disptchr.h%""
		end

	cwel_release_dispatcher_object is
		external
			"C [macro %"disptchr.h%"]"
		end

--	cwel_my_print is
--		external
--			"C [macro %"disptchr.h%"]"
--		alias
--			"myPrint"
--		end




	cwin_def_window_proc (hwnd: POINTER; msg, wparam,
			lparam: INTEGER): INTEGER is
			-- SDK DefWindowProc
		external
			"C [macro <windows.h>] (HWND, UINT, WPARAM, LPARAM): EIF_INTEGER"
		alias
			"DefWindowProc"
		end

end -- class WEL_DISPATCHER


--|----------------------------------------------------------------
--| Windows Eiffel Library: library of reusable components for ISE Eiffel.
--| Copyright (C) 1986-2001 Interactive Software Engineering Inc.
--| All rights reserved. Duplication and distribution prohibited.
--| May be used only with ISE Eiffel, under terms of user license. 
--| Contact ISE for any other use.
--|
--| Interactive Software Engineering Inc.
--| ISE Building
--| 360 Storke Road, Goleta, CA 93117 USA
--| Telephone 805-685-1006, Fax 805-685-6869
--| Electronic mail <info@eiffel.com>
--| Customer support: http://support.eiffel.com>
--| For latest info see award-winning pages: http://www.eiffel.com
--|----------------------------------------------------------------

