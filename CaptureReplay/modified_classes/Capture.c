#include <stdio.h>
#include <time.h>
//#include <dos.h>
#include <windows.h>

#include "Capture.h"

time_t timeCourant (void)
{

  /*struct time tps;*/
  time_t res;

  SYSTEMTIME tps;
  GetSystemTime (&tps);
  //gettime (&tps);
//  printf ("\ntime courant nOK\n");
  res = (tps.wHour * 360000) + 
	(tps.wMinute * 60 * 100) + 
	(tps.wSecond * 100) + (tps.wMilliseconds / 10);
//printf ("temps C (milieme/s) : %d\n\n", res);
  //return time (NULL);*/
return res;
}

/*
 
int main ()
{
  struct time tps;
  int tmp = 0;
  gettime (&tps);
  printf ("heure : %d\n", tps.ti_hour);
  printf ("minute : %d\n", tps.ti_min);
  printf ("seconde : %d\n", tps.ti_sec);
  printf ("100eme sec: %d\n", tps.ti_hund);
  printf ("temp en 100eme: %d\n", (tps.ti_hour * 360000) + (tps.ti_min * 60 * 100) + (tps.ti_sec * 100) + tps.ti_hund);
  printf ("temp en 100eme: %d\n", timeCourant ());
  printf ("fin\n");
  return 0;
}*/
