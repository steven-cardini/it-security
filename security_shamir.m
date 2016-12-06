%% Implementation of Shamir's
% Steven Cardini 10.11.2016

clear variables;
close all;
clc; % clear command window

recalculate_secret = false;

%% Configure parameters

S = 190503180520;
a1 = 482943028839;
a2 = 1206749628665;
p = 1234567890133;
k = 3;
n = 4;

%% Calculate the secrets Sx to be divided

Sx = zeros(n,1);

for x=1:n
  Sx(1,x) = mod (S + a1*x + a2*x^2, p);
  fprintf('(%d, %d)\n', x, Sx(1,x));
end


%% Reconstruct the secret S

sum = 0;
for i=1:k
  factor = 1;
  for j=1:k
    if j==i
      continue;
    end
    factor = factor * ( (0-j) / (i-j) );
  end
  sum = sum + Sx(1,i) * factor;
end

S_decrypted = mod(sum, p);
if (recalculate_secret)
  fprintf('\nS = %d\n', S_decrypted);
end
