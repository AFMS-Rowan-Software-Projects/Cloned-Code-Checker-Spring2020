// function example
#include <iostream>
using namespace std;

int subtraction (int a, int b)
{
  int r;
  r=a-b;
  return r;
}

int addition (int a, int b)
{
  int r;
  r=a+b;
  return r;
}

void duplicate (int& a, int& b, int& c)
{
  a*=2;
  b*=2;
  c*=2;
}

int divide (int a, int b=2)
{
  int r;
  r=a/b;
  return (r);
}