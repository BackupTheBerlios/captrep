indexing
	description: "Objects that ..."
	author: ""
	date: "$Date: 2004/05/27 16:10:48 $"
	revision: "$Revision: 1.3 $"

class
	CAPTURE_REPLAY_SHARED_INFO	

inherit
	CAPTURE_REPLAY
	WEL_WM_CONSTANTS
	
feature {WEL_WINDOW}

	register (a: WEL_WINDOW) is
			-- store 'a' in the list of created WEL_WINDOW
			do
				reg_windows.extend (a)
			end

feature {EV_ANY, WEL_APPLICATION, CAPTURE_REPLAY}

	set_is_delegated_to_windows is
			-- set 'is_delegated_to_windows'
		do
			delegated_to_windows.set_item (true)
		end
		
	unset_is_delegated_to_windows is
			-- unset 'is_delegated_to_windows'
		do
			delegated_to_windows.set_item (false)
		end		
		
	is_delegated_to_windows : BOOLEAN is
			-- is event handling delegated to windows ?
		do
			Result := delegated_to_windows.item
		end
		
	is_capture : BOOLEAN is
			-- is capture mode ?
		do
			Result := capture.item
		end
		
	is_replay : BOOLEAN is
			-- is replay mode
		do
			Result := replay.item
		end		
		
feature {NONE} -- implementation

	capture: BOOLEAN_REF is
		-- is capture mode ?
		indexing
			once_status: global

		once
			create result
		end
	
	replay: BOOLEAN_REF is
			-- is replay mode ?
		indexing
			once_status: global
		once
			create result	
		end		
		
	delegated_to_windows: BOOLEAN_REF is
			-- is replay mode ?
		indexing
			once_status: global
		once
			create result	
		end		
	
feature {WEL_DISPATCHER}

	windows_captured_event : CAPTURE_REPLAY_INFO is
			-- Next event captured from windows
			do
				Result := repository.item
				display_info (Result)
				repository.forth
			end	
			
feature {EV_ANY}
			
	capture_windows_event (w: WEL_WINDOW; m: INTEGER; wp: INTEGER; lp: INTEGER) is
		-- capture events catched directly by windows
		local
			tmp: CAPTURE_REPLAY_INFO
			time_tmp : INTEGER
		do
time_tmp :=ccapture_get_time_courant - time_lancement.item 
--time_tmp := 2
			create tmp.make_internal (w, m, wp, lp, time_tmp)
			repository.extend (tmp)
			display_info (tmp)
		end

	remove_capture_replay_info (e: like capture_replay_info) is
			-- remove 'e' from repository
			require
				capture_replay_info = e
			do
				repository.remove
			end

	capture_replay_info : CAPTURE_REPLAY_INFO is
			-- current capture_replay_info
			do
				check
					repository /= void
				end
				result := repository.item
			end
			
feature {NONE} -- internal searches

	widget_by_pointer (a: POINTER) : WEL_WINDOW is
			-- find widget
			do
				if tools.is_window (a) then
					Result := tools.window_of_item (a)
				else
					io.putstring("Error: pointer does not correspond to a WEL_WINDOW - widget_by_pointer %N")
				end
			end

	info_by_poi (a: CAPTURE_REPLAY_POI) : CAPTURE_REPLAY_INFO is
			-- find info related to capture/replay
			local
				stop : BOOLEAN
				i : INTEGER
			do
				i := repository.index
				from
					repository.start
					stop := repository.after
				until stop
				loop
					if repository.item.target_identifier.is_equal (a) then
						stop := true
						result := repository.item
					else
						if repository.islast then
							stop := true
						else
							repository.forth
						end
					end
				end
				repository.go_i_th (i)
			end		
			
	widget_by_poi (a: CAPTURE_REPLAY_POI) : WEL_WINDOW is
			-- find widget
			local
				stop : BOOLEAN
				i : INTEGER
			do
				i := reg_windows.index
				stop := false
				from
					reg_windows.start
				until stop
				loop
					if reg_windows.item.match_poi (a) then
						stop := true
						result := reg_windows.item
					else
						if reg_windows.islast then
							stop := true
						else
							reg_windows.forth
						end
					end
				end
				display_current_window_info
				reg_windows.go_i_th (i)
			end
				
feature {NONE} -- display of trace on io or dedicated file

	type_captured (e: INTEGER; f: like trace_file) is
			-- filter messages
		require
			f /= void and then f.is_open_write
		do
			inspect
				e
			when Wm_char, Wm_syschar, Wm_keydown,
			     Wm_keyup, Wm_syskeydown, Wm_syskeyup
			     then
			     	f.putstring ("Event is related to keyboard %N")
			when Wm_mousemove, Wm_mousewheel, Wm_ncmousemove -- , Wm_mousefirst
			--ajouté
							, Wm_ncmouseleave, Wm_mouseleave

			     then
			     	f.putstring ("Event is related to mouse %N")
			when Wm_lbuttondown, Wm_rbuttondown, Wm_mbuttondown, 
			     Wm_lbuttonup, Wm_rbuttonup, Wm_mbuttonup,
			     Wm_xbuttondown, Wm_xbuttonup,Wm_xbuttondblclk,
			     Wm_lbuttondblclk, Wm_rbuttondblclk, Wm_mbuttondblclk,
			     Wm_nclbuttondown, Wm_ncrbuttondown, Wm_ncmbuttondown, 
			     Wm_nclbuttonup, Wm_ncrbuttonup, Wm_ncmbuttonup
                 then
			     	f.putstring ("Event is related to mouse button %N")
			when Wm_command, Wm_menucommand  then
			     	f.putstring ("Event is related to command %N")
			when Wm_menuselect, Wm_menuchar, Wm_menurbuttonup, Wm_menudrag, Wm_menugetobject,
			     Wm_uninitmenupopup  
			      then
			     	f.putstring ("Event is related to menu %N")
			when Wm_vscroll, Wm_hscroll  then
			     	f.putstring ("Event is related to scroll %N")
			     	
			--ajouté
			when Wm_paint then
					f.putstring ("Event is related to PAINT %N")
			when Wm_syscommand then
					f.putstring ("Event is related to maximize, minimize close %N")

			else
				f.putstring ("Event is not known %N")
			end
		end
		
	display_not_captured (e: WEL_MSG; f: like trace_file) is
		-- display 'not captured'
		do
			display_not_captured_msg (e.hwnd, e.message, e.lparam, e.wparam, trace_file)
		end

	display_not_captured_msg (hwnd: POINTER; msg, wparam, lparam: INTEGER; f: like trace_file) is
		-- display 'not captured'
		require
			f /= void and then f.is_open_write
		do
			f.putstring(" Message not captured ");
		   	f.putint (msg);
			f.put_new_line	
		end


	display_info_not_created (e: WEL_MSG; f: like trace_file) is
		-- display 'not captured'
		do
			display_info_not_created_msg (e.hwnd, e.message, e.lparam, e.wparam, trace_file)
		end
		

	display_info_not_created_msg (hwnd: POINTER; msg, wparam, lparam: INTEGER; f: like trace_file) is
		-- display 'not captured'
		require
			f /= void and then f.is_open_write
		do
			f.putstring(" Capture/replay info not created ");
			f.putint (msg);
			f.put_new_line
		end



	display_info_on_file (info : CAPTURE_REPLAY_INFO ; f: like trace_file) is
		-- display captured info
		require
			f /= void and then f.is_open_write
		do
			f.putstring (" ------------------------------------------------ %N");
			f.putstring ("captured info %N");
			type_captured (info.message, f)
			f.putstring (" identifier : ")
			info.target_identifier.display_on_file (f);
			f.putstring ("%N message  : ")
			f.putint (info.message)
			f.putstring ("%N lparam : ")
			f.putint (info.lparam)
			f.putstring ("%N wparam : ")
			f.putint (info.wparam)
			f.putstring ("%N last_boolean_result : ")
			f.putbool (info.last_boolean_result)
			f.putstring ("%N dispatch_result : ")
			f.putint (info.dispatch_result)
			f.putstring ("%N temps écoulé depuis lancement : ")
			f.putint (info.time_replay)
			f.putstring (" %N ------------------------------------------------ %N");
		end	

	display_info (info : CAPTURE_REPLAY_INFO) is
		-- display captured info
		do
			display_info_on_file (info, io.output)
		end
		
	trace_info (tmp: CAPTURE_REPLAY_INFO) is
			-- trace information in file 'trace'
		require			
			trace_file /= void and trace_file.is_open_write
		do
			display_info_on_file (tmp, trace_file)
		end
		
		display_current_window_info is
			-- find widget
			local
				i : INTEGER
			do
				i := reg_windows.index
				from
					reg_windows.start
				until reg_windows.islast
				loop
						reg_windows.item.display_poi
						reg_windows.forth
				end
				reg_windows.go_i_th (i)
			end	
		
feature {CAPTURE_REPLAY} -- Implementation
	
	reg_windows : LINKED_LIST [WEL_WINDOW] is
			-- contains all the registered WEL_WINDOW
		indexing
			once_status: global
			once
				create result.make
			end
		
	unique_number : INTEGER_REF is
			-- contain the order number associated to last created WEL_WINDOW
		indexing
			once_status: global
		once
			create Result
		end
		
	repository : LINKED_LIST [CAPTURE_REPLAY_INFO] is
		indexing
			once_status: global

			-- contains all informations to memorize for replay
			once
				create Result.make
			end

	trace_file : PLAIN_TEXT_FILE is
			-- contains all informations to memorize for replay
			require
				name_of_trace_file /= void and then name_of_trace_file.count > 0
			once
				create Result.make_open_write (name_of_trace_file)
			ensure
				Result.is_open_write
			end
			
	time_lancement : INTEGER_REF	is
		indexing
			once_status: global

		once
			create Result
		end
		
feature {CAPTURE_REPLAY}

	tools : WEL_WINDOWS_ROUTINES is
			-- tools
		once
			create Result
		end
		
	name_of_trace_file : STRING
	-- name of file that will contain traces


feature {NONE} -- external

		ccapture_get_time_courant : INTEGER   is
				-- Récupere le temps actuel
			external
				"C () : EIF_INTEGER| %"Capture.h%""
			alias
				"timeCourant"
			end
				


	
invariant
	invariant_clause: True -- Your invariant here

end -- class CAPTURE_REPLAY_SHARED_INFO
