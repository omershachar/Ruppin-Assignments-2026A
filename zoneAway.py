'''
TODO
- [ ] add function that target specific folders
- [ ] print the number of ZoneShit that were there and how many removed 
- [ ] if a specific folder isn't mentioned assumes this folder by default
'''

import subprocess

def remove_zone_identifiers():
    # Define the bash command
    command = 'find . -type f -name "*:Zone.Identifier" -delete'
    
    try:
        # shell=True allows using shell features like wildcards (*)
        subprocess.run(command, shell=True, check=True)
        print("Successfully deleted all Zone.Identifier files.")
    except subprocess.CalledProcessError as e:
        print(f"Error occurred: {e}")

# Commands overview:
# find .: Start searching in the current directory.
# -type f: Look for files only.
# -name "*:Zone.Identifier": Match files ending with that specific string.
# -delete: Delete the matched files immediately.

if __name__ == "__main__":
    remove_zone_identifiers()
