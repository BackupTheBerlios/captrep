indexing
	description: "Objects that ..."
	author: ""
	date: "$Date: 2004/05/27 16:10:48 $"
	revision: "$Revision: 1.2 $"

class
	REPLAY_THREAD

inherit
	THREAD -- CAPTURE ADDON
	rename
			execute as execute_replay
	end


	CAPTURE_REPLAY -- pour accerder aux fonctions de CAPTURE_REPLAY_HANDLING


create 
--	make,
	make_reply_thread





feature {NONE} -- Implementation

--	make is do  end

	make_reply_thread (eva : EV_APPLICATION_IMP) is
	do
		ev_app := eva
	ensure
		ev_app /= void
	end

	execute_replay is
		-- Le corp du thread qui va rejouer les événements capturés comme on le ferrai dans message_loop de EV_APPLICATION_IMP
--	local
--		msg: WEL_MSG
--	do
--		from
--			create msg.make
--		until
--			ev_app.is_last_captured
--		loop
--			ev_app.set_with_next_captured_event (msg)		-- CAPTURE ADDON
--			if msg.last_boolean_result then
--				ev_app.process_message (msg)
--			else
--				if not ev_app.internal_idle_actions.is_empty then
--					ev_app.internal_idle_actions.call (Void)
--				elseif ev_app.idle_actions_internal /= Void and then
--					not ev_app.idle_actions_internal.is_empty then 
--					ev_app.idle_actions_internal.call (Void)
--				else
--					msg.wait
--				end
--			end
--		end
--		io.putstring ("%NPlus d'événement à rejouer%N") 
	local 
	 i : INTEGER
	 STOP : BOOLEAN	 
	do
		io.putstring ("%NNombre d'element (repository) : ")
		io.put_integer (ev_app.repository.count)
		if 0 = ev_app.repository.count  then
			STOP := true
		end 
		
		from
			i := ev_app.repository.index
			ev_app.repository.start
		until
			stop
		loop
			io.putstring ("%NValeur per process (repository fils) (")
			io.put_integer (ev_app.repository.index)
			io.putstring (") = ")
			io.put_integer (ev_app.repository.item.lparam)
			if ev_app.repository.islast then
				stop := true
			else
				ev_app.repository.forth
			end
		end
		ev_app.repository.go_i_th (i)


		io.putstring ("%NNombre d'element (reg_windows fils) : ")
		io.put_integer (ev_app.reg_windows.count)

		if 0 = ev_app.reg_windows.count  then
			STOP := true
		end 
		
		from
			i := ev_app.reg_windows.index
			ev_app.reg_windows.start
		until
			stop
		loop
			io.putstring ("%NValeur per process (reg_window) (")
			io.put_integer (ev_app.reg_windows.index)
			io.putstring (") = ")
			io.put_integer (ev_app.reg_windows.item.x)
			if ev_app.repository.islast then
				stop := true
			else
				ev_app.repository.forth
			end
		end
		ev_app.repository.go_i_th (i)
		
	end


--	execute  is
--			-- 
--	do
--		execute_replay	
--	end
		

feature -- attribut
	ev_app : EV_APPLICATION_IMP

invariant
	invariant_clause: True -- Your invariant here

end -- class REPLAY_THREAD
