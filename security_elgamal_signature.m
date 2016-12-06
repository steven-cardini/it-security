%% Implementation of ElGamal digital signature
% Steven Cardini 22.11.2016

clear variables;
close all;
clc; % clear command window

%% Configure parameters

p = 19;
PrK = 17; % Alice's private key, 2 <= PrK <= (p-2)
r = 2; % a primitive root modulo p
s = mod(r^PrK, p);
a = 223; % ephemeral secret, gcd(p-1, a) = 1
b = 13; % a*b mod (p-1) = 1 --> b is modular inverse of a

M = [12, 5, 7, 2, 14, 1, 18, 11]; % messages that Alice wants to send to Bob, M < p


%% Calculate the signature

u = mod(r^a, p);
v = mod (b .* (M - PrK.*u), p-1);

fprintf('Alice sends the following messages to Bob:');
M
fprintf('Alongside the messages, Alice sends u = %d as well as the following parts of the signature:', u);
v

fprintf('For each message, Bob calculates c1=(s^u * u^v) and c2=(r^M) mod p, and accepts the signature if c1 = c2:');
c1 = mod (mod(s^u, p) .* mod(u.^v, p), p)
c2 = mod(r.^M, p)



