#include <bits/stdc++.h>
using namespace std;

struct bin_node
{
  string data;
  bin_node* left;
  bin_node* right;

  bin_node(string d, bin_node* l, bin_node* r)
  {
    data = d;
    left = l;
    right = r;
  }
};

tuple<bin_node*, int, int> largest_complete_subtree(bin_node* root)
{
  if (!root)
  {
    return make_tuple(root, -1, -1);
  }
  bin_node* l_root;
  bin_node* r_root;
  int l_max, l_curr, r_max, r_curr;
  tie(l_root, l_max, l_curr) = largest_complete_subtree(root->left);
  tie(r_root, r_max, r_curr) = largest_complete_subtree(root->right);
  int new_curr;
  if (l_curr == r_curr)
  {
    new_curr = l_curr + 1;
  }
  else if (l_curr >= 0 && r_curr >= 0)
  {
    new_curr = 1;
  }
  else
  {
    new_curr = 0;
  }
  if (new_curr >= l_max && new_curr >= r_max)
  {
    return make_tuple(root, new_curr, new_curr);
  }
  if (l_max >= r_max)
  {
    return make_tuple(l_root, l_max, new_curr);
  }
  return make_tuple(r_root, r_max, new_curr);
}

#define BN(n, l, r) bin_node* n = new bin_node(#n, l, r)

int main()
{
  // example in the problem statement
  BN(aq, NULL, NULL);
  BN(ap, NULL, NULL);
  BN(ao, NULL, NULL);
  BN(an, NULL, NULL);
  BN(am, NULL, NULL);
  BN(al, NULL, NULL);
  BN(ak, NULL, NULL);
  BN(aj, ap, aq);
  BN(ai, an, ao);
  BN(ah, al, am);
  BN(ag, NULL, NULL);
  BN(af, NULL, NULL);
  BN(ae, NULL, NULL);
  BN(ad, NULL, NULL);
  BN(ac, aj, ak);
  BN(ab, NULL, NULL);
  BN(aa, NULL, NULL);
  BN(z, ah, ai);
  BN(y, NULL, NULL);
  BN(x, af, ag);
  BN(w, ad, ae);
  BN(v, ab, ac);
  BN(u, z, aa);
  BN(t, x, y);
  BN(s, NULL, NULL);
  BN(r, NULL, NULL);
  BN(q, v, w);
  BN(p, t, u);
  BN(o, NULL, NULL);
  BN(n, NULL, NULL);
  BN(m, r, s);
  BN(l, NULL, NULL);
  BN(k, NULL, NULL);
  BN(j, p, q);
  BN(i, n, o);
  BN(h, l, m);
  BN(g, j, k);
  BN(f, NULL, NULL);
  BN(e, NULL, NULL);
  BN(d, h, i);
  BN(c, f, g);
  BN(b, d, e);
  BN(a, b, c);

  bin_node* root;
  int max_d, curr_d;
  tie(root, max_d, curr_d) = largest_complete_subtree(a);
  cout << root->data << ", md:" << max_d << ", cd:" << curr_d;
}