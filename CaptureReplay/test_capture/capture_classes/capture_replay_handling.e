indexing
	description: "Objects that ..."
	author: ""
	date: "$Date: 2004/05/17 14:45:46 $"
	revision: "$Revision: 1.1 $"

class
	CAPTURE_REPLAY_HANDLING

inherit
	STORABLE	
	CAPTURE_REPLAY_SHARED_INFO
	
create
	init_capture_replay 

feature {WEL_APPLICATION} -- capture/replay basic operations
	
	temporize_if_needed is
			-- wait a little bit for some events
			require
				app_implementation /= void
			do
				-- should be refined according to type of event
				app_implementation.sleep(30)
			end
		
	capture_event (e: WEL_MSG) is
		-- add an event to repository of event if reccordable
		require
			e /= void
			-- trace_file /= void and trace_file.is_open_write
			is_capture and not is_replay
		local
			tmp1: WEL_WINDOW
			tmp2: CAPTURE_REPLAY_INFO
		do
			if is_candidate (e) then
				tmp1 := widget_by_pointer (e.hwnd)
				if tmp1 /= void then
			    	create tmp2.make (e, tmp1)
		    	end
				if tmp2 = void then
					debug ("CAPTURE_TRACE") 
			  			display_info_not_created (e, trace_file)
			  		end
					debug ("CAPTURE_STDOUT") 
			  			display_info_not_created (e, io.output)
			  		end
				else
			  		repository.extend (tmp2)
					debug ("CAPTURE_TRACE") 
				   		trace_info (tmp2)
			  		end
					debug ("CAPTURE_STDOUT") 
				   		display_info (tmp2)
			  		end

				end
			else
				debug ("CAPTURE_TRACE") 
					display_not_captured (e, trace_file)
				end
				debug ("CAPTURE_STDOUT") 
			  		display_not_captured (e, io.output)
			  	end
			end
		end
		
	is_last_captured : BOOLEAN is
			-- is last captured event ?
		do
			if repository.islast then
				Result := true
			end
		end
		
	set_with_next_captured_event (e: WEL_MSG) is
			-- return next event to handle
			require
				e /= void
				-- trace_file /= void and trace_file.is_open_write
			local
				tmp1: CAPTURE_REPLAY_INFO
				tmp2 : WEL_WINDOW
			do
				tmp1 := repository.item
				debug ("CAPTURE_STDOUT") 
					display_info (tmp1)
				end
				debug ("CAPTURE_TRACE") 
					trace_info (tmp1)
				end
				tmp2 := widget_by_poi (tmp1.target_identifier)
				if tmp2 /= void then
			  		e.set_message (tmp1.message)
			  		e.set_lparam(tmp1.lparam)
			  		e.set_wparam(tmp1.wparam)
			  		e.set_hwnd (tmp2.item)

					debug ("CAPTURE_TRACE") 
				  		trace_file.putstring ("%N Found : Widget is found in memory as it should be %N")
			  		end

			  	else
					debug ("CAPTURE_TRACE") 
				  		trace_file.putstring ("%N Found : Widget is found in memory as it should be %N")
			  		end
					debug ("CAPTURE_STDOUT") 
						io.putstring ("%N Error : Widget is not found in memory as it should be %N")
			  		end
				end
				repository.forth
			end		

feature {NONE} -- loading / saving	

	retrieve_captured_events is
			-- initialize repository
			require
				is_replay and not is_capture
			local
				tmp : CAPTURE_REPLAY_HANDLING
			do
				tmp ?= retrieve_by_name (name_of_event_db)
				if tmp /= void then
					repository.merge_right (tmp.internal_repository)
					repository.start
				end
			end
		
	store_captured_events is
			-- initialize repository
			do
				internal_repository.merge_right (repository)
				store_by_name (name_of_event_db);			
			 	io.putstring("Capture is finished .... %N")
			end	
			
feature {NONE} -- implementation
	
	init_capture_replay (app : EV_APPLICATION_IMP)is
			-- Initialize `Current'.
		local
			f: PLAIN_TEXT_FILE
		do
			app_implementation := app
			create f.make (name_of_event_db);
			if f.exists then
			  io.putstring("Replay is undergoing ....%N")
			  init_replay
			else	
			  io.putstring("Capture is undergoing .... %N")
			  init_capture
			end
		end
		
	init_capture is
			-- init capture capabilities
			do
				capture.set_item (true)
				create internal_repository.make
				name_of_trace_file := "capture.txt"
			ensure
				is_capture and not is_replay
				trace_file.is_open_write
			end

	init_replay is
			-- init replay capabilities
			do
				replay.set_item (true)
				retrieve_captured_events
				repository.start
				name_of_trace_file := "replay.txt"
--Affichage de nombre d'événements récupéré
io.putstring ("Nombre evenement enregistré : ")
io.put_integer (repository.count)
io.putstring ("%N")
io.putstring ("Nombre evenement enregistré : ")
io.put_integer (reg_windows.count)
io.putstring ("%N")
			ensure
				not is_capture and is_replay
				trace_file.is_open_write
			end	

feature {NONE} -- conversion of event informations to/from persistent format
		
		is_candidate (e: WEL_MSG) : BOOLEAN is
			-- filter messages
		do
			inspect
				e.message
			when Wm_char, Wm_syschar, Wm_keydown,
			     Wm_keyup, Wm_syskeydown, Wm_syskeyup
			     then
			     	--io.putstring ("Event is related to keyboard %N")
					Result := true
			when Wm_mousemove, Wm_mousewheel, Wm_ncmousemove -- , Wm_mousefirst
				--Ajouté 
				, Wm_ncmouseleave, Wm_mouseleave
			     then
			     	--io.putstring ("Event is related to mouse %N")
					Result := true
			when Wm_lbuttondown, Wm_rbuttondown, Wm_mbuttondown, 
			     Wm_lbuttonup, Wm_rbuttonup, Wm_mbuttonup,
			     Wm_xbuttondown, Wm_xbuttonup,Wm_xbuttondblclk,
			     Wm_lbuttondblclk, Wm_rbuttondblclk, Wm_mbuttondblclk,
			     Wm_nclbuttondown, Wm_ncrbuttondown, Wm_ncmbuttondown, 
			     Wm_nclbuttonup, Wm_ncrbuttonup, Wm_ncmbuttonup
                 then
			     	--io.putstring ("Event is related to mouse button %N")
					Result := true
			when Wm_command, Wm_menucommand  then
			     	--io.putstring ("Event is related to command %N")
					Result := true
			when Wm_menuselect, Wm_menuchar, Wm_menurbuttonup, Wm_menudrag, Wm_menugetobject,
			     Wm_uninitmenupopup  
			      then
			     	--io.putstring ("Event is related to menu %N")
					Result := true
			when Wm_vscroll, Wm_hscroll  then
			     	--io.putstring ("Event is related to scroll %N")
					Result := true
			when Wm_paint then
					--io.putstring ("Event is related to PAINT %N")
					Result := true
			when Wm_syscommand then
					--io.putstring ("Event is related to maximize, minimize close %N")
					Result := true				
			else
				Result := false	
			end
		end

feature {CAPTURE_REPLAY}
	
	app_implementation : EV_APPLICATION_IMP
	-- access to application
	
	internal_repository : LINKED_LIST [CAPTURE_REPLAY_INFO]

	name_of_event_db : STRING is "File_Of_Events"
	-- name of file where events are stored

invariant
	invariant_clause: is_capture xor is_replay 

end -- class CAPTURE_REPLAY_HANDLING
